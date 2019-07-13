package com.dmitrysimakov.kilogram.ui

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.Person
import com.dmitrysimakov.kilogram.databinding.ActivityMainBinding
import com.dmitrysimakov.kilogram.databinding.NavHeaderMainBinding
import com.dmitrysimakov.kilogram.util.*
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import timber.log.Timber
import javax.inject.Inject

private const val RC_SIGN_IN = 1

class MainActivity : DaggerAppCompatActivity() {
    
    @Inject lateinit var preferences: SharedPreferences
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    private val viewModel by lazy { getViewModel(this, viewModelFactory) }
    
    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }
    private val navBinding by lazy { NavHeaderMainBinding.bind(binding.navView.getHeaderView(0)) }
    
    private val navController by lazy { findNavController(R.id.fragment_container) }
    
    private val authStateListener by lazy { FirebaseAuth.AuthStateListener { auth ->
        viewModel.user.value = auth.currentUser?.takeIf { it.isEmailVerified }
    }}
    
    private var timerIsRunning = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        
        binding.lifecycleOwner = this
        binding.vm = viewModel
        navBinding.lifecycleOwner = this
        navBinding.vm = viewModel
    
        viewModel.user.observe(this, Observer { binding.navView.menu.findItem(R.id.chatsFragment).isVisible = it != null })
        viewModel.timerIsRunning.observe(this, Observer { timerIsRunning = it })
        
        viewModel.timerIsRunning.value = preferences.getBoolean(PreferencesKeys.TIMER_IS_RUNNING, false)
        viewModel.sessionStartMillis = preferences.getLong(PreferencesKeys.SESSION_START_MILLIS, 0)
        viewModel.restStartMillis = preferences.getLong(PreferencesKeys.REST_START_MILLIS, 0)
        viewModel.restTime.value = preferences.getInt(PreferencesKeys.REST_TIME, 0).takeIf { it > 0 }
        
        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
        
        val shareIntent = ShareCompat.IntentBuilder.from(this)
                .setText("Kilogram - крутой фитнес трекер. Переходи по ссылке и тренируйся вместе со мной.")
                .setType("text/plain")
                .intent
        val shareItem = binding.navView.menu.findItem(R.id.share)
        if (shareIntent.resolveActivity(packageManager) == null) {
            shareItem.isVisible = false
        } else {
            shareItem.setOnMenuItemClickListener {
                startActivity(shareIntent)
                true
            }
        }
        
        navBinding.authBtn.setOnClickListener {
            if (viewModel.user.value == null) {
                startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(listOf(
                                AuthUI.IdpConfig.EmailBuilder().build(),
                                AuthUI.IdpConfig.GoogleBuilder().build(),
                                AuthUI.IdpConfig.FacebookBuilder().build()))
                        .setLogo(R.mipmap.ic_launcher_round)
                        .build(), RC_SIGN_IN)
            } else {
                AuthUI.getInstance().signOut(this)
                if (navController.currentDestination?.id == R.id.messagesFragment) {
                    navController.popBackStack(R.id.mainFragment, true)
                }
            }
        }
    
        requestEmailVerification()
    }
    
    override fun onStart() {
        Timber.d("onStart")
        super.onStart()
        if (viewModel.timerIsRunning.value!!) viewModel.startTimer()
    }
    
    override fun onResume() {
        Timber.d("onResume")
        super.onResume()
        auth.addAuthStateListener(authStateListener)
    }
    
    override fun onPause() {
        Timber.d("onPause")
        auth.removeAuthStateListener(authStateListener)
        super.onPause()
    }
    
    override fun onStop() {
        Timber.d("onStop")
        if (timerIsRunning) viewModel.stopTimer()
        super.onStop()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
            binding.drawerLayout.closeDrawer(binding.navView)
        } else {
            super.onBackPressed()
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            userDocument.get().addOnSuccessListener { doc ->
                FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener{ result ->
                    val token = result.token
                    if (!doc.exists()) {
                        createNewPersonWith(token)
                    } else {
                        addTokenToPerson(token, doc.toObject(Person::class.java)!!)
                    }
                }
            }
    
            requestEmailVerification()
        }
    }
    
    private fun createNewPersonWith(token: String) {
        userDocument.set(Person(
                user?.displayName ?: "",
                user?.photoUrl.toString(),
                mutableListOf(token)
        ))
    }
    
    private fun addTokenToPerson(token: String, person: Person) {
        val tokens = person.registrationTokens
        if (!tokens.contains(token)) {
            tokens.add(token)
            userDocument.update(mapOf("registrationTokens" to tokens))
        }
    }
    
    private fun requestEmailVerification() {
        if (user?.isEmailVerified == false) {
            startActivity(Intent(this, EmailVerificationActivity::class.java))
        }
    }
}

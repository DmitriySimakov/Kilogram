package com.dmitrysimakov.kilogram.ui

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.ActivityMainBinding
import com.dmitrysimakov.kilogram.databinding.NavHeaderMainBinding
import com.dmitrysimakov.kilogram.util.PreferencesKeys
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.app_bar_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

private const val RC_SIGN_IN = 1

class MainActivity : AppCompatActivity() {
    
    private val preferences: SharedPreferences by inject()
    
    private val vm: SharedViewModel by viewModel()
    
    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }
    private val navBinding by lazy { NavHeaderMainBinding.bind(binding.navView.getHeaderView(0)) }
    
    private val navController by lazy { findNavController(R.id.fragment_container) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        
        binding.lifecycleOwner = this
        binding.vm = vm
        navBinding.lifecycleOwner = this
        navBinding.vm = vm
    
        vm.user.observe(this, Observer {user ->
            listOf(R.id.chatsFragment, R.id.peopleFragment).forEach {
                binding.navView.menu.findItem(it).isVisible = user != null
            }
        })
        vm.timerIsRunning.observe(this, Observer { })
        
        vm.timerIsRunning.value = preferences.getBoolean(PreferencesKeys.TIMER_IS_RUNNING, false)
        vm.sessionStartMillis = preferences.getLong(PreferencesKeys.SESSION_START_MILLIS, 0)
        vm.restStartMillis = preferences.getLong(PreferencesKeys.REST_START_MILLIS, 0)
        vm.restTime.value = preferences.getInt(PreferencesKeys.REST_TIME, 0).takeIf { it > 0 }
        
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
            if (vm.user.value == null) {
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
        if (vm.timerIsRunning.value == true) vm.startTimer()
    }
    
    override fun onResume() {
        Timber.d("onResume")
        super.onResume()
        vm.addAuthStateListener()
    }
    
    override fun onPause() {
        Timber.d("onPause")
        vm.removeAuthStateListener()
        super.onPause()
    }
    
    override fun onStop() {
        Timber.d("onStop")
        if (vm.timerIsRunning.value == true) vm.stopTimer()
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
            vm.initUser()
            requestEmailVerification()
        }
    }
    
    private fun requestEmailVerification() {
        vm.requestEmailVerification {
            startActivity(Intent(this, EmailVerificationActivity::class.java))
        }
    }
}

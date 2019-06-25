package com.dmitrysimakov.kilogram.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.ActivityMainBinding
import com.dmitrysimakov.kilogram.databinding.NavHeaderMainBinding
import com.dmitrysimakov.kilogram.util.PreferencesKeys
import com.dmitrysimakov.kilogram.util.getViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    
    val RC_SIGN_IN = 1
    
    @Inject lateinit var preferences: SharedPreferences
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    private val viewModel by lazy { getViewModel(this, viewModelFactory) }
    
    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }
    private val navBinding by lazy { NavHeaderMainBinding.bind(binding.navView.getHeaderView(0)) }
    
    private val navController by lazy { findNavController(R.id.fragment_container) }
    
    private val auth = FirebaseAuth.getInstance()
    private val authStateListener by lazy { FirebaseAuth.AuthStateListener {
        viewModel.username.value = auth.currentUser?.displayName
    }}
    
    private var timerIsRunning = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        fun getShareIntent() = ShareCompat.IntentBuilder.from(this)
                .setText("Kilogram - крутой фитнес трекер. Переходи по ссылке и тренируйся вместе со мной.")
                .setType("text/plain")
                .intent
        
        binding.lifecycleOwner = this
        binding.vm = viewModel
        navBinding.lifecycleOwner = this
        navBinding.vm = viewModel
        
        viewModel.timerIsRunning.observe(this, Observer { timerIsRunning = it })
        
        viewModel.timerIsRunning.value = preferences.getBoolean(PreferencesKeys.TIMER_IS_RUNNING, false)
        viewModel.sessionStartMillis = preferences.getLong(PreferencesKeys.SESSION_START_MILLIS, 0)
        viewModel.restStartMillis = preferences.getLong(PreferencesKeys.REST_START_MILLIS, 0)
        viewModel.restTime.value = preferences.getInt(PreferencesKeys.REST_TIME, 0).takeIf { it > 0 }
        
        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
        
        val shareItem = binding.navView.menu.findItem(R.id.share)
        if (getShareIntent().resolveActivity(packageManager) == null) {
            shareItem.isVisible = false
        } else {
            shareItem.setOnMenuItemClickListener {
                startActivity(getShareIntent())
                true
            }
        }
        
        navBinding.authBtn.setOnClickListener {
            if (viewModel.username.value == null) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(listOf(
                                        AuthUI.IdpConfig.EmailBuilder().build(),
                                        AuthUI.IdpConfig.GoogleBuilder().build()))
                                .build(),
                        RC_SIGN_IN)
            } else {
                AuthUI.getInstance().signOut(this)
            }
        }
    }
    
    override fun onStart() {
        super.onStart()
        if (viewModel.timerIsRunning.value!!) viewModel.startTimer()
    }
    
    override fun onResume() {
        super.onResume()
        auth.addAuthStateListener(authStateListener)
    }
    
    override fun onPause() {
        auth.removeAuthStateListener(authStateListener)
        super.onPause()
    }
    
    override fun onStop() {
        super.onStop()
        if (timerIsRunning) viewModel.stopTimer()
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
}

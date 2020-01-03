package com.dmitrysimakov.kilogram.ui

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.ActivityMainBinding
import com.dmitrysimakov.kilogram.util.PreferencesKeys
import com.dmitrysimakov.kilogram.util.setupWithNavController
import com.dmitrysimakov.kilogram.util.toast
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

const val RC_SIGN_IN = 1

class MainActivity : AppCompatActivity() {
    
    private val preferences: SharedPreferences by inject()
    
    private val vm: SharedViewModel by viewModel()
    
    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }
    
    private var currentNavController: LiveData<NavController>? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        
        binding.lifecycleOwner = this
        binding.vm = vm
    
        vm.initUser()
        vm.timerIsRunning.observe(this) { }
        
        vm.timerIsRunning.value = preferences.getBoolean(PreferencesKeys.TIMER_IS_RUNNING, false)
        vm.sessionStartMillis = preferences.getLong(PreferencesKeys.SESSION_START_MILLIS, 0)
        vm.restStartMillis = preferences.getLong(PreferencesKeys.REST_START_MILLIS, 0)
        vm.restTime.value = preferences.getInt(PreferencesKeys.REST_TIME, 0).takeIf { it > 0 }
        
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }
    
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }
    
    override fun onStart() {
        Timber.d("onStart")
        super.onStart()
        if (vm.timerIsRunning.value == true) vm.startTimer()
    }
    
    override fun onStop() {
        Timber.d("onStop")
        if (vm.timerIsRunning.value == true) vm.stopTimer()
        super.onStop()
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            vm.signIn()
            val user = FirebaseAuth.getInstance().currentUser!!
            if (!user.isEmailVerified) {
                user.sendEmailVerification().addOnCompleteListener {
                    if (it.isSuccessful) toast(getString(R.string.verify_your_email))
                }
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
    
    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
                R.navigation.home,
                R.navigation.catalog,
                R.navigation.subscriptions,
                R.navigation.search,
                R.navigation.profile
        )
        val controller = binding.bottomNavView.setupWithNavController(
                navGraphIds,
                supportFragmentManager,
                R.id.nav_host_fragment,
                intent
        )
        
        controller.observe(this) { navController ->
            setupActionBarWithNavController(navController)
        }
        currentNavController = controller
    }
}

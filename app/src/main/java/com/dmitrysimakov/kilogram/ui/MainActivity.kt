package com.dmitrysimakov.kilogram.ui

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.ActivityMainBinding
import com.dmitrysimakov.kilogram.util.PreferencesKeys
import com.dmitrysimakov.kilogram.util.setupWithNavController
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

private const val RC_SIGN_IN = 1

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
    
        vm.timerIsRunning.observe(this, Observer { })
        
        vm.timerIsRunning.value = preferences.getBoolean(PreferencesKeys.TIMER_IS_RUNNING, false)
        vm.sessionStartMillis = preferences.getLong(PreferencesKeys.SESSION_START_MILLIS, 0)
        vm.restStartMillis = preferences.getLong(PreferencesKeys.REST_START_MILLIS, 0)
        vm.restTime.value = preferences.getInt(PreferencesKeys.REST_TIME, 0).takeIf { it > 0 }
        
        setSupportActionBar(binding.toolbar)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
        
//        val shareIntent = ShareCompat.IntentBuilder.from(this)
//                .setText("Kilogram - крутой фитнес трекер. Переходи по ссылке и тренируйся вместе со мной.")
//                .setType("text/plain")
//                .intent
//        val shareItem = binding.navView.menu.findItem(R.id.share)
//        if (shareIntent.resolveActivity(packageManager) == null) {
//            shareItem.isVisible = false
//        } else {
//            shareItem.setOnMenuItemClickListener {
//                startActivity(shareIntent)
//                true
//            }
//        }
        
//        navBinding.authBtn.setOnClickListener {
//            if (vm.user.value == null) {
//                startActivityForResult(AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setAvailableProviders(listOf(
//                                AuthUI.IdpConfig.EmailBuilder().build(),
//                                AuthUI.IdpConfig.GoogleBuilder().build(),
//                                AuthUI.IdpConfig.FacebookBuilder().build()))
//                        .setLogo(R.mipmap.ic_launcher_round)
//                        .build(), RC_SIGN_IN)
//            } else {
//                AuthUI.getInstance().signOut(this)
//                if (navController.currentDestination?.id == R.id.messagesFragment) {
//                    navController.popBackStack(R.id.mainFragment, true)
//                }
//            }
//        }
    
        requestEmailVerification()
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
            vm.initUser()
            requestEmailVerification()
        }
    }
    
    private fun requestEmailVerification() {
        vm.requestEmailVerification {
            startActivity(Intent(this, EmailVerificationActivity::class.java))
        }
    }
    
    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
                R.navigation.dieries,
                R.navigation.catalogs,
                R.navigation.community,
                R.navigation.menu
        )
        val controller = binding.bottomNavView.setupWithNavController(
                navGraphIds,
                supportFragmentManager,
                R.id.nav_host_fragment,
                intent
        )
    
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }
    
    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}

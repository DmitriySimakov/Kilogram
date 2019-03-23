package com.dmitrysimakov.kilogram.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.ActivityMainBinding
import com.dmitrysimakov.kilogram.util.PreferencesKeys
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    
    @Inject lateinit var preferences: SharedPreferences
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    private val viewModel by lazy { getViewModel(this, viewModelFactory) }
    
    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }
    
    private val navController by lazy { findNavController(R.id.fragment_container) }
    
    private var timerIsRunning = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding.lifecycleOwner = this
        viewModel.timerIsRunning.observe(this, Observer { timerIsRunning = it })
        binding.vm = viewModel
        
        viewModel.timerIsRunning.value = preferences.getBoolean(PreferencesKeys.TIMER_IS_RUNNING, false)
        viewModel.sessionStartMillis = preferences.getLong(PreferencesKeys.SESSION_START_MILLIS, 0)
        viewModel.restStartMillis = preferences.getLong(PreferencesKeys.REST_START_MILLIS, 0)
        viewModel.restTime.value = preferences.getInt(PreferencesKeys.REST_TIME, 0).takeIf { it > 0 }
        
        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)

        binding.navView.setupWithNavController(navController)
    }
    
    override fun onStart() {
        super.onStart()
        if (viewModel.timerIsRunning.value!!) viewModel.startTimer()
    }
    
    override fun onStop() {
        super.onStop()
        if (timerIsRunning) viewModel.stopTimer()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawer_layout)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}

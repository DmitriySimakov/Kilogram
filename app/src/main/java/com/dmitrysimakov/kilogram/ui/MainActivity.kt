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
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    private lateinit var viewModel: MainViewModel
    
    private lateinit var binding: ActivityMainBinding
    
    @Inject lateinit var preferences: SharedPreferences
    
    private lateinit var navController: NavController
    
    private var timerIsRunning = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setLifecycleOwner(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.timerIsRunning.observe(this, Observer { timerIsRunning = it })
        binding.viewModel = viewModel
        
        viewModel.timerIsRunning.value = preferences.getBoolean(PreferencesKeys.TIMER_IS_RUNNING, false)
        viewModel.sessionStartMillis = preferences.getLong(PreferencesKeys.SESSION_START_MILLIS, 0)
        viewModel.restStartMillis = preferences.getLong(PreferencesKeys.REST_START_MILLIS, 0)
        
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.fragment_container)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)

        nav_view.setupWithNavController(navController)
    }
    
    override fun onStart() {
        super.onStart()
        if (viewModel.timerIsRunning.value!!) viewModel.startTimer()
    }
    
    override fun onStop() {
        super.onStop()
        if (timerIsRunning) viewModel.stopTimer()
        preferences.edit().putBoolean(PreferencesKeys.TIMER_IS_RUNNING, timerIsRunning).apply()
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

package com.dmitrysimakov.kilogram.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.app.ShareCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
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
    
    private val b by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }
    
    private val navController by lazy { findNavController(R.id.fragment_container) }
    
    private var timerIsRunning = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        fun getShareIntent() = ShareCompat.IntentBuilder.from(this)
                .setText("Kilogram - крутой фитнес трекер. Переходи по ссылке и тренируйся вместе со мной.")
                .setType("text/plain")
                .intent
        
        b.lifecycleOwner = this
        viewModel.timerIsRunning.observe(this, Observer { timerIsRunning = it })
        b.vm = viewModel
        
        viewModel.timerIsRunning.value = preferences.getBoolean(PreferencesKeys.TIMER_IS_RUNNING, false)
        viewModel.sessionStartMillis = preferences.getLong(PreferencesKeys.SESSION_START_MILLIS, 0)
        viewModel.restStartMillis = preferences.getLong(PreferencesKeys.REST_START_MILLIS, 0)
        viewModel.restTime.value = preferences.getInt(PreferencesKeys.REST_TIME, 0).takeIf { it > 0 }
        
        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController, b.drawerLayout)
        NavigationUI.setupWithNavController(b.navView, navController)
        
        val shareItem = b.navView.menu.findItem(R.id.share)
        if (getShareIntent().resolveActivity(packageManager) == null) {
            shareItem.isVisible = false
        } else {
            shareItem.setOnMenuItemClickListener {
                startActivity(getShareIntent())
                true
            }
        }
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
        return NavigationUI.navigateUp(navController, b.drawerLayout)
    }

    override fun onBackPressed() {
        if (b.drawerLayout.isDrawerOpen(b.navView)) {
            b.drawerLayout.closeDrawer(b.navView)
        } else {
            super.onBackPressed()
        }
    }
}

package com.dmitrysimakov.kilogram.ui

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.util.PreferencesKeys
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject

class MainViewModel @Inject constructor(private val preferences: SharedPreferences) : ViewModel() {
    
    private var timer: Timer? = null
    
    val timerIsRunning = MutableLiveData(false)
    
    var sessionStartMillis = 0L
    
    var restStartMillis = 0L
    
    val sessionTime = MutableLiveData<Int>()
    
    val restTime = MutableLiveData<Int>()
    
    fun startTimer() {
        timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                val now = System.currentTimeMillis()
                sessionTime.postValue(((now - sessionStartMillis)/1000).toInt())
                if (restStartMillis != 0L) {
                    restTime.postValue(((now - restStartMillis) / 1000).toInt())
                }
            }
        }
        timer?.scheduleAtFixedRate(task, 0, 1000)
    }
    
    fun stopTimer() {
        timer?.cancel()
        timer = null
    }
    
    fun onTrainingSessionStarted() {
        sessionStartMillis = System.currentTimeMillis()
        restStartMillis = 0L
        
        timerIsRunning.value = true
        startTimer()
        preferences.edit().putLong(PreferencesKeys.SESSION_START_MILLIS, sessionStartMillis).apply()
    }
    
    fun onTrainingSessionFinished() {
        timerIsRunning.value = false
    }
    
    fun onSetCompleted() {
        restStartMillis = System.currentTimeMillis()
    }
}

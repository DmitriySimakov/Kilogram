package com.dmitrysimakov.kilogram.ui

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.util.FirebaseUserLiveData
import com.dmitrysimakov.kilogram.util.PreferencesKeys
import com.dmitrysimakov.kilogram.util.userDocument
import com.google.firebase.iid.FirebaseInstanceId
import java.util.*

class SharedViewModel(private val preferences: SharedPreferences) : ViewModel() {
    
    val programDayId = MutableLiveData(0L)
    
    //region Firebase
    val user = FirebaseUserLiveData()
    
    fun initUser() {
        userDocument.get().addOnSuccessListener { doc ->
            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener{ result ->
                val token = result.token
                if (!doc.exists()) {
                    createNewUserWithToken(token)
                } else {
                    addTokenToUser(token, doc.toObject(User::class.java)!!)
                }
            }
        }
    }
    
    private fun createNewUserWithToken(token: String) {
        userDocument.set(User(
                user.value?.displayName ?: "",
                user.value?.photoUrl.toString(),
                mutableListOf(token)
        ))
    }
    
    private fun addTokenToUser(token: String, user: User) {
        val tokens = user.registrationTokens
        if (!tokens.contains(token)) {
            tokens.add(token)
            userDocument.update(mapOf("registrationTokens" to tokens))
        }
    }
    //endregion
    
    //region Timer
    private var timer: Timer? = null
    
    val timerIsRunning = MutableLiveData(false)
    
    var sessionStartMillis = 0L
    
    var restStartMillis = 0L
    
    val elapsedSessionTime = MutableLiveData<Int?>()
    
    val elapsedRestTime = MutableLiveData<Int?>()
    
    val restTime = MutableLiveData<Int?>()
    
    fun startTimer() {
        timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                val now = System.currentTimeMillis()
                elapsedSessionTime.postValue(((now - sessionStartMillis)/1000).toInt())
                if (restStartMillis != 0L) {
                    elapsedRestTime.postValue(((now - restStartMillis) / 1000).toInt())
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
        timerIsRunning.value = true
        clearValues()
        startTimer()
        
        preferences.edit()
                .putBoolean(PreferencesKeys.TIMER_IS_RUNNING, true)
                .putLong(PreferencesKeys.SESSION_START_MILLIS, sessionStartMillis)
                .apply()
    }
    
    fun onTrainingSessionFinished() {
        stopTimer()
        timerIsRunning.value = false
        clearValues()
        
        preferences.edit()
                .putBoolean(PreferencesKeys.TIMER_IS_RUNNING, false)
                .remove(PreferencesKeys.SESSION_START_MILLIS)
                .remove(PreferencesKeys.REST_START_MILLIS)
                .remove(PreferencesKeys.REST_TIME)
                .apply()
    }
    
    fun onSetCompleted(rest: Int) {
        restStartMillis = System.currentTimeMillis()
        restTime.value = rest
        
        preferences.edit()
                .putLong(PreferencesKeys.REST_START_MILLIS, restStartMillis)
                .putInt(PreferencesKeys.REST_TIME, rest)
                .apply()
    }
    
    private fun clearValues() {
        elapsedSessionTime.value = null
        elapsedRestTime.value = null
        restTime.value = null
    }
    //endregion
}

package com.dmitrysimakov.kilogram.ui

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.FirebaseDao
import com.dmitrysimakov.kilogram.data.Person
import com.dmitrysimakov.kilogram.util.PreferencesKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.iid.FirebaseInstanceId
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val preferences: SharedPreferences,
        private val firebase: FirebaseDao
) : ViewModel() {
    
    val programDayId = MutableLiveData(0L)
    
    //region Firebase
    val user = MutableLiveData<FirebaseUser?>()
    
    private val authStateListener by lazy { FirebaseAuth.AuthStateListener { auth ->
        user.value = auth.currentUser?.takeIf { it.isEmailVerified }
    }}
    
    fun addAuthStateListener() {
        firebase.auth.addAuthStateListener(authStateListener)
    }
    
    fun removeAuthStateListener() {
        firebase.auth.removeAuthStateListener(authStateListener)
    }
    
    fun initUser() {
        firebase.userDocument.get().addOnSuccessListener { doc ->
            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener{ result ->
                val token = result.token
                if (!doc.exists()) {
                    createNewPersonWith(token)
                } else {
                    addTokenToPerson(token, doc.toObject(Person::class.java)!!)
                }
            }
        }
    }
    
    fun requestEmailVerification(callback: (()-> Unit)) {
        if (firebase.user?.isEmailVerified == false) {
            callback()
        }
    }
    
    private fun createNewPersonWith(token: String) {
        firebase.userDocument.set(Person(
                firebase.user?.displayName ?: "",
                firebase.user?.photoUrl.toString(),
                mutableListOf(token)
        ))
    }
    
    private fun addTokenToPerson(token: String, person: Person) {
        val tokens = person.registrationTokens
        if (!tokens.contains(token)) {
            tokens.add(token)
            firebase.userDocument.update(mapOf("registrationTokens" to tokens))
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

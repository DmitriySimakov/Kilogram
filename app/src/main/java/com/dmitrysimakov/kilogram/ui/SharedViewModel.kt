package com.dmitrysimakov.kilogram.ui

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.remote.Subscriptions
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.util.*
import com.dmitrysimakov.kilogram.util.live_data.AbsentLiveData
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.google.firebase.iid.FirebaseInstanceId
import java.util.*

class SharedViewModel(private val preferences: SharedPreferences) : ViewModel() {
    
    val programDayId = MutableLiveData(0L)
    
    //region Firebase
    
    private val _userExist = MutableLiveData<Boolean>()
    val user: LiveData<User> = _userExist.switchMap { userExist ->
        if (!userExist) AbsentLiveData.create()
        else userDocument.liveData { it.toObject(User::class.java)!! }
    }
    
    fun initUser() {
        firebaseUser?.let { _userExist.value = true }
    }
    
    fun signOut() { _userExist.value = false }
    
    fun signIn() {
        userDocument.get().addOnSuccessListener { doc ->
            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener{ result ->
                if (!doc.exists()) createNewUser(result.token)
                else addToken(result.token)
            }
        }
    }
    
    private fun createNewUser(token: String) {
        val newUser = User(firebaseUser!!.uid, firebaseUser!!.displayName ?: "", firebaseUser!!.photoUrl.toString())
        firestore.batch()
                .set(userDocument, newUser)
                .set(tokensDocument, mapOf("tokens" to listOf(token)))
                .set(subscriptionsDocument, Subscriptions())
                .commit()
                .addOnSuccessListener { _userExist.value = true }
    }
    
    private fun addToken(token: String) {
        tokensDocument.get().addOnSuccessListener { doc ->
            @Suppress("UNCHECKED_CAST")
            val tokens = doc["tokens"] as MutableList<String>
            if (!tokens.contains(token)) {
                tokens.add(token)
                tokensDocument.update("tokens", tokens).addOnSuccessListener {
                    _userExist.value = true
                }
            }
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
        
        preferences.edit {
            putBoolean(PreferencesKeys.TIMER_IS_RUNNING, true)
            putLong(PreferencesKeys.SESSION_START_MILLIS, sessionStartMillis)
        }
    }
    
    fun onTrainingSessionFinished() {
        stopTimer()
        timerIsRunning.value = false
        clearValues()
        
        preferences.edit {
            putBoolean(PreferencesKeys.TIMER_IS_RUNNING, false)
            remove(PreferencesKeys.SESSION_START_MILLIS)
            remove(PreferencesKeys.REST_START_MILLIS)
            remove(PreferencesKeys.REST_TIME)
        }
    }
    
    fun onSetCompleted(rest: Int) {
        restStartMillis = System.currentTimeMillis()
        restTime.value = rest
        
        preferences.edit {
            putLong(PreferencesKeys.REST_START_MILLIS, restStartMillis)
            putInt(PreferencesKeys.REST_TIME, rest)
        }
    }
    
    private fun clearValues() {
        elapsedSessionTime.value = null
        elapsedRestTime.value = null
        restTime.value = null
    }
    //endregion
}

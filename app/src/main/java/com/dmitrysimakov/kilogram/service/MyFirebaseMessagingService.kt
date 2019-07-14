package com.dmitrysimakov.kilogram.service

import com.dmitrysimakov.kilogram.data.remote.FirebaseDao
import com.dmitrysimakov.kilogram.data.remote.Person
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class MyFirebaseMessagingService : FirebaseMessagingService() {
    
    @Inject lateinit var firebase: FirebaseDao
    
    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }
    
    override fun onNewToken(token: String?) {
        firebase.user?.let { sendRegistrationToServer(token) }
    }
    
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Timber.d("From: ${remoteMessage?.from}")
        remoteMessage?.data?.isNotEmpty()?.let {
            Timber.d("Message data payload: ${remoteMessage.data}")
        }
        remoteMessage?.notification?.let {
            Timber.d("Message Notification Body: ${it.body}")
        }
    }
    
    private fun sendRegistrationToServer(token: String?) {
        if (token == null) throw NullPointerException("FCM token is null.")
    
        val userDocument = FirebaseFirestore.getInstance().document("users/${firebase.user!!.uid}")
        userDocument.get().addOnSuccessListener {
            val user = it.toObject(Person::class.java)!!
            val tokens = user.registrationTokens
            if (!tokens.contains(token)) {
                tokens.add(token)
                userDocument.update(mapOf("registrationTokens" to tokens))
            }
        }
    }
}
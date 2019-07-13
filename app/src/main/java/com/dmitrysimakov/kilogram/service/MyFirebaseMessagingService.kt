package com.dmitrysimakov.kilogram.service

import com.dmitrysimakov.kilogram.data.Person
import com.dmitrysimakov.kilogram.util.user
import com.dmitrysimakov.kilogram.util.userDocument
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class MyFirebaseMessagingService : FirebaseMessagingService() {
    
    override fun onNewToken(token: String?) {
        user?.let { sendRegistrationToServer(token) }
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
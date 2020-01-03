package com.dmitrysimakov.kilogram.data.remote

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
        var name: String = "",
        var photoUrl: String? = null,
        val registrationTokens: MutableList<String> = mutableListOf(),
        @get:Exclude var id: String = ""
)

fun DocumentSnapshot.toUser() = toObject(User::class.java).also { user -> user?.id = id }
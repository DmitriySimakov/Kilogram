package com.dmitrysimakov.kilogram.data.remote

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
        var name: String = "",
        var photoUrl: String? = null,
        var followersCount: Int = 0,
        var followedCount: Int = 0,
        @get:Exclude var id: String = ""
)

fun DocumentSnapshot.toUser() = toObject(User::class.java)!!.also { it.id = id }
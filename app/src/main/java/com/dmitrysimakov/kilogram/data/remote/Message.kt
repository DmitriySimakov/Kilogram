package com.dmitrysimakov.kilogram.data.remote

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Message(
        val senderId: String = "",
        val text: String? = null,
        val imageUrl: String? = null,
        val timestamp: Date = Date(),
        val wasRead: Boolean = false,
        @get:Exclude var id: String = ""
)

fun DocumentSnapshot.toMessage() = toObject(Message::class.java)!!.also { it.id = id }
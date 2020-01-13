package com.dmitrysimakov.kilogram.data.remote

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Message(
        var senderId: String = "",
        var text: String? = null,
        var imageUrl: String? = null,
        var timestamp: Date = Date(),
        var wasRead: Boolean = false,
        @get:Exclude var id: String = ""
)

fun DocumentSnapshot.toMessage() = toObject(Message::class.java)!!.also { it.id = id }
package com.dmitrysimakov.kilogram.data.remote

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Chat(
        val companion: User = User(),
        val lastMessage: Message = Message(),
        @get:Exclude var id: String = ""
)

fun DocumentSnapshot.toChat() = toObject(Chat::class.java)!!.also { it.id = id }
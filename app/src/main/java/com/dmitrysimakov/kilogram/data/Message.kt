package com.dmitrysimakov.kilogram.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Message(
        var sender: Sender = Sender(),
        var text: String? = null,
        var imageUrl: String? = null,
        var timestamp: Timestamp? = null,
        @get:Exclude var id: String = ""
) {
    data class Sender(
            val id: String? = null,
            @get:Exclude var name: String = "",
            @get:Exclude var photoUrl: String? = null
    )
}
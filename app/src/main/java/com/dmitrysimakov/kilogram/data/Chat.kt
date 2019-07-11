package com.dmitrysimakov.kilogram.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Chat(
        @get:Exclude var id: String = "",
        var name: String? = null,
        var photoUrl: String? = null,
        var lastMessage: LastMessage = LastMessage(),
        var members: List<Member> = emptyList()
) {
    data class LastMessage(
            val senderId: String = "",
            var text: String? = null,
            val timestamp: Timestamp? = null,
            val messageId: String = ""
    )
    
    data class Member(
            val id: String = "",
            val name: String = "",
            val photoUrl: String? = null
    )
}
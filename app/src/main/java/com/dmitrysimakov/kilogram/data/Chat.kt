package com.dmitrysimakov.kilogram.data

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Chat(
        var members: List<Member> = emptyList(),
        var membersIds: List<String> = emptyList(),
        var name: String? = null,
        var photoUrl: String? = null,
        var lastMessage: LastMessage = LastMessage(),
        @get:Exclude var id: String = ""
) {
    data class LastMessage(
            val senderId: String = "",
            var text: String? = null,
            val timestamp: Date = Date(),
            val messageId: String = ""
    )
    
    data class Member(
            val id: String = "",
            val name: String = "",
            val photoUrl: String? = null
    )
}
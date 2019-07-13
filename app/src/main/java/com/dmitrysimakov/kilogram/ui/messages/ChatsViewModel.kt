package com.dmitrysimakov.kilogram.ui.messages

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.Chat
import com.dmitrysimakov.kilogram.data.FirebaseDao
import com.dmitrysimakov.kilogram.util.live_data.liveData
import javax.inject.Inject

class ChatsViewModel @Inject constructor(firebase: FirebaseDao) : ViewModel() {
    
    val userId = firebase.user!!.uid
    
    private val unsortedChats = firebase.chatsCollection.whereArrayContains("membersIds", userId).liveData { doc ->
        doc.toObject(Chat::class.java)!!.also { chat ->
            chat.id = doc.id
            val others = chat.members.filter { it.id != userId }
            if (chat.name == null) {
                chat.name = others.joinToString(", ") { it.name }
            }
            if (chat.members.size == 2) {
                chat.photoUrl = others.first().photoUrl
            }
        }
    }
    
    val chats = Transformations.map(unsortedChats) { chats ->
        chats.sortedByDescending { it.lastMessage.timestamp }
    }
}
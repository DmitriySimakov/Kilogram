package com.dmitrysimakov.kilogram.ui.community.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.dmitrysimakov.kilogram.data.remote.Chat
import com.dmitrysimakov.kilogram.util.chatsCollection
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.user

class ChatsViewModel : ViewModel() {
    
    private val unsortedChats = chatsCollection.whereArrayContains("membersIds", user!!.uid).liveData { doc ->
        doc.toObject(Chat::class.java)!!.also { chat ->
            chat.id = doc.id
            val others = chat.members.filter { it.id != user!!.uid }
            if (chat.name == null) {
                chat.name = others.joinToString(", ") { it.name }
            }
            if (chat.members.size == 2) {
                chat.photoUrl = others.first().photoUrl
            }
        }
    }
    
    val chats = unsortedChats.map { chats ->
        chats.sortedByDescending { it.lastMessage.timestamp }
    }
}
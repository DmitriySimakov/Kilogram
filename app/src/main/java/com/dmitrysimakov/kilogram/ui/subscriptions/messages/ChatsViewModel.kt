package com.dmitrysimakov.kilogram.ui.subscriptions.messages

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.remote.Chat
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.util.chatsCollection
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.setNewValue

class ChatsViewModel : ViewModel() {
    
    private val _user = MutableLiveData<User>()
    
    private val unsortedChats = _user.switchMap { user ->
        chatsCollection.whereArrayContains("membersIds", user.id).liveData { doc ->
            doc.toObject(Chat::class.java)!!.also { chat ->
                chat.id = doc.id
                val others = chat.members.filter { it.id != user.id }
                if (chat.name == null) {
                    chat.name = others.joinToString(", ") { it.name }
                }
                if (chat.members.size == 2) {
                    chat.photoUrl = others.first().photoUrl
                }
            }
        }
    }
    
    val chats = unsortedChats.map { chats ->
        chats.sortedByDescending { it.lastMessage.timestamp }
    }
    
    fun setUser(user: User) { _user.setNewValue(user) }
}
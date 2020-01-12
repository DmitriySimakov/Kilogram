package com.dmitrysimakov.kilogram.ui.subscriptions.messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.data.remote.toChat
import com.dmitrysimakov.kilogram.util.chatsCollection
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.setNewValue

class ChatsViewModel : ViewModel() {
    
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    
    private val unsortedChats = chatsCollection.liveData { doc -> doc.toChat() }
    
    val chats = unsortedChats.map { chats ->
        chats.sortedByDescending { it.lastMessage.timestamp }
    }
    
    fun setUser(user: User) { _user.setNewValue(user) }
}
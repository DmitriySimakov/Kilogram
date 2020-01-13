package com.dmitrysimakov.kilogram.ui.subscriptions.messages

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.remote.Chat
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.data.remote.toChat
import com.dmitrysimakov.kilogram.util.chatsCollection
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.setNewValue

class ChatsViewModel : ViewModel() {
    
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user
    
    private val unsortedChats = user.switchMap { user ->
        if (user != null) chatsCollection.liveData { doc -> doc.toChat() }
        else MutableLiveData<List<Chat>>(null)
    }
    
    val chats = unsortedChats.map { chats ->
        chats.sortedByDescending { it.lastMessage.timestamp }
    }
    
    fun setUser(user: User?) { _user.setNewValue(user) }
}
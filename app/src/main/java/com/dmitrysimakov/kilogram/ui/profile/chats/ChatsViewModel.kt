package com.dmitrysimakov.kilogram.ui.profile.chats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.remote.models.Chat
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.util.chatsCollection
import com.dmitrysimakov.kilogram.util.live_data.liveData

class ChatsViewModel : ViewModel() {
    
    val user = MutableLiveData<User?>()
    
    private val unsortedChats = user.switchMap { user ->
        if (user != null) chatsCollection.liveData { doc -> doc.toObject(Chat::class.java)!! }
        else MutableLiveData<List<Chat>>(null)
    }
    
    val chats = unsortedChats.map { chats ->
        chats.sortedByDescending { it.lastMessage.timestamp }
    }
}
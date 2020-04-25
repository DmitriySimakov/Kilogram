package com.dmitrysimakov.kilogram.ui.messages.chats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.remote.data_sources.MessageSource
import com.dmitrysimakov.kilogram.util.live_data.AbsentLiveData

class ChatsViewModel(private val messageSrc: MessageSource) : ViewModel() {
    
    val user = MutableLiveData<User?>()
    
    private val unsortedChats = user.switchMap { user ->
        if (user == null) AbsentLiveData.create()
        else messageSrc.chatsLive()
    }
    
    val chats = unsortedChats.map { chats ->
        chats.sortedByDescending { it.lastMessage.timestamp }
    }
}
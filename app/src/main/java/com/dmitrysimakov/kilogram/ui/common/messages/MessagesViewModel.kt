package com.dmitrysimakov.kilogram.ui.common.messages

import android.net.Uri
import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.model.Chat
import com.dmitrysimakov.kilogram.data.model.Message
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.remote.data_sources.FirebaseStorageSource
import com.dmitrysimakov.kilogram.data.remote.data_sources.MessageSource
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch

class MessagesViewModel(
        private val messageSrc: MessageSource,
        private val firebaseStorage: FirebaseStorageSource
) : ViewModel() {
    
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user
    
    val chat = MutableLiveData<Chat>()
    
    val messages = chat.switchMap { messageSrc.messagesLive(it.companion.id) }
    
    fun start(user: User?, companionId: String) { viewModelScope.launch {
        _user.setNewValue(user)
        user?.let { chat.setNewValue(messageSrc.chat(user, companionId)) }
    }}
    
    fun sendMessage(text: String?, imageUrl: String?) {
        val message = Message(user.value!!.id, text, imageUrl)
        messageSrc.sendMessage(message, chat.value!!.companion.id)
    }
    
    fun sendImage(uri: Uri) { viewModelScope.launch {
        val imageUri = firebaseStorage.uploadImage(uri)
        sendMessage(null, imageUri)
    }}
    
    fun markNewMessagesAsRead() {
        messages.value?.let { messageSrc.markNewMessagesAsRead(it, chat.value!!.companion.id) }
    }
}
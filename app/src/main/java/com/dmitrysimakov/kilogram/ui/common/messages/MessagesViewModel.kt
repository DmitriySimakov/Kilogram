package com.dmitrysimakov.kilogram.ui.common.messages

import android.net.Uri
import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.model.Chat
import com.dmitrysimakov.kilogram.data.model.Message
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.remote.data_sources.FirebaseStorageSource
import com.dmitrysimakov.kilogram.data.remote.data_sources.MessagesSource
import com.dmitrysimakov.kilogram.data.remote.generateId
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch

class MessagesViewModel(
        private val messagesSrc: MessagesSource,
        private val firebaseStorage: FirebaseStorageSource
) : ViewModel() {
    
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user
    
    val chat = MutableLiveData<Chat>()
    
    val messages = chat.switchMap { messagesSrc.messagesLive(it.companion.id) }
    
    fun start(user: User?, companionId: String) { viewModelScope.launch {
        _user.setNewValue(user)
        user?.let { chat.setNewValue(messagesSrc.chat(user, companionId)) }
    }}
    
    fun sendMessage(text: String?, imageUrl: String?) {
        val message = Message(generateId(), user.value!!.id, text, imageUrl)
        messagesSrc.sendMessage(message, chat.value!!.companion.id)
    }
    
    fun sendImage(uri: Uri) { viewModelScope.launch {
        val imageUri = firebaseStorage.uploadImage(uri)
        sendMessage(null, imageUri)
    }}
    
    fun markNewMessagesAsRead() {
        messages.value?.let { messagesSrc.markNewMessagesAsRead(it, chat.value!!.companion.id) }
    }
}
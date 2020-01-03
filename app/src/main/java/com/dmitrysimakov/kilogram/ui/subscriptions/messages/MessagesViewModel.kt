package com.dmitrysimakov.kilogram.ui.subscriptions.messages

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.remote.Chat
import com.dmitrysimakov.kilogram.data.remote.Message
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.util.chatsCollection
import com.dmitrysimakov.kilogram.util.firestore
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.msgImagesRef
import com.dmitrysimakov.kilogram.util.setNewValue
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference

class MessagesViewModel : ViewModel() {
    
    private val _user = MutableLiveData<User>()
    
    private lateinit var chatDocument: DocumentReference
    private lateinit var messagesCollection: CollectionReference
    
    private val _chat = MutableLiveData<Chat>()
    val chat: LiveData<Chat> = _chat
    
    fun setChatId(id: String) {
        chatDocument = chatsCollection.document(id)
        messagesCollection = chatDocument.collection("messages")
        chatDocument.get().addOnSuccessListener { chatDoc ->
            _chat.setNewValue(chatDoc.toObject(Chat::class.java)?.also { it.id = id })
        }
    }
    
    val messages = _chat.switchMap {
        messagesCollection.orderBy("start_time").liveData { doc ->
            doc.toObject(Message::class.java)?.also { msg ->
                msg.id = doc.id
                val sender = _chat.value!!.members.find { member -> member.id == msg.sender.id }
                msg.sender.name = sender?.name ?: ""
                msg.sender.photoUrl = sender?.photoUrl
            }
        }
    }
    
    fun setUser(user: User) { _user.setNewValue(user) }
    
    fun sendMessage(text: String?, imageUrl: String?) {
        _chat.value?.let {
            val lastMessage = Chat.LastMessage(_user.value?.photoUrl, text ?: "Фотография")
            firestore.batch()
                    .set(messagesCollection.document(), Message(Message.Sender(_user.value?.id), text, imageUrl))
                    .update(chatDocument, "lastMessage", lastMessage)
                    .commit()
        }
    }
    
    fun sendImage(uri: Uri) {
        val imageRef = msgImagesRef.child(uri.lastPathSegment!!)
        imageRef.putFile(uri).addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { sendMessage(null, it.toString()) }
        }
    }
}
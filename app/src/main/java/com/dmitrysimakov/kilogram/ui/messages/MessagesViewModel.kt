package com.dmitrysimakov.kilogram.ui.messages

import android.net.Uri
import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.remote.Chat
import com.dmitrysimakov.kilogram.data.remote.Message
import com.dmitrysimakov.kilogram.util.*
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference

class MessagesViewModel : ViewModel() {
    
    private val userId = user!!.uid
    
    private lateinit var chatDocument: DocumentReference
    private lateinit var messagesCollection: CollectionReference
    
    private val _chat = MutableLiveData<Chat>()
    val chat: LiveData<Chat>
        get() = _chat
    
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
    
    fun sendMessage(text: String?, imageUrl: String?) {
        _chat.value?.let {
            firestore.batch()
                    .set(messagesCollection.document(), Message(Message.Sender(userId), text, imageUrl))
                    .update(chatDocument, "lastMessage", Chat.LastMessage(userId, text ?: "Фотография"))
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
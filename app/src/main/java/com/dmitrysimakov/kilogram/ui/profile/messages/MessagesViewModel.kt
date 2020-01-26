package com.dmitrysimakov.kilogram.ui.profile.messages

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.remote.models.Chat
import com.dmitrysimakov.kilogram.data.remote.models.Message
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.util.*
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference

class MessagesViewModel : ViewModel() {
    
    private val _user = MutableLiveData<User>()
    val user: LiveData<User?> = _user
    
    private lateinit var userChatDoc: DocumentReference
    private lateinit var companionChatDoc: DocumentReference
    private lateinit var userMessagesCol: CollectionReference
    private lateinit var companionMessagesCol: CollectionReference
    
    private val _chat = MutableLiveData<Chat>()
    val chat: LiveData<Chat> = _chat
    
    val messages = _chat.switchMap {
        userMessagesCol.orderBy("timestamp").liveData { it.toObject(Message::class.java)!! }
    }
    
    fun start(user: User?, companionId: String) {
        _user.setNewValue(user)
        if (user == null) return
        
        userChatDoc = chatsCollection.document(companionId)
        companionChatDoc = usersCollection.document(companionId).collection("chats").document(user.id)
        userMessagesCol = userChatDoc.collection("messages")
        companionMessagesCol = companionChatDoc.collection("messages")
        
        userChatDoc.get().addOnSuccessListener {
            if (it.exists()) {
                _chat.setNewValue(it.toObject(Chat::class.java)!!)
            } else {
                usersCollection.document(companionId).get().addOnSuccessListener { companionDoc ->
                    val companion = companionDoc.toObject(User::class.java)!!
                    val newChatForUser = Chat(companion.id, companion)
                    val newChatForCompanion = Chat(user.id, user)
                    _chat.setNewValue(newChatForUser)
                    
                    firestore.batch()
                            .set(userChatDoc, newChatForUser)
                            .set(companionChatDoc, newChatForCompanion)
                            .commit()
                }
            }
        }
    }
    
    fun sendMessage(text: String?, imageUrl: String?) {
        val senderId = user.value!!.id
        val userMessageDoc = userMessagesCol.document()
        val message = Message(userMessageDoc.id, senderId, text, imageUrl)
        firestore.batch()
                .set(userMessageDoc, message)
                .set(companionMessagesCol.document(userMessageDoc.id), message)
                .update(userChatDoc, "lastMessage", message)
                .update(companionChatDoc, "lastMessage", message)
                .commit()
    }
    
    fun sendImage(uri: Uri) {
        val imageRef = msgImagesRef.child(uri.lastPathSegment!!)
        imageRef.putFile(uri).addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { sendMessage(null, it.toString()) }
        }
    }
    
    fun markNewMessagesAsRead() {
        val user = user.value ?: return
    
        var newMessagesReceived = false
        val writeBatch = firestore.batch()
        messages.value?.forEach { msg ->
            if (msg.senderId != user.id && !msg.wasRead) {
                newMessagesReceived = true
                writeBatch
                        .update(userMessagesCol.document(msg.id), "wasRead", true)
                        .update(companionMessagesCol.document(msg.id), "wasRead", true)
            }
        }
        if (newMessagesReceived) {
            writeBatch
                    .update(userChatDoc, "lastMessage.wasRead", true)
                    .update(companionChatDoc, "lastMessage.wasRead", true)
        }
        writeBatch.commit()
    }
}
package com.dmitrysimakov.kilogram.ui.subscriptions.messages

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.remote.*
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
        userMessagesCol.orderBy("timestamp").liveData { it.toMessage() }
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
                _chat.setNewValue(it.toChat())
            } else {
                usersCollection.document(companionId).get().addOnSuccessListener { companionDoc ->
                    val newChatForUser = Chat(companionDoc.toUser())
                    val newChatForCompanion = Chat(user)
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
        val message = Message(senderId, text, imageUrl)
        firestore.batch()
                .set(userMessagesCol.document(), message)
                .set(companionMessagesCol.document(), message)
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
}
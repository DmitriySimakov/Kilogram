package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.lifecycle.LiveData
import com.dmitrysimakov.kilogram.data.model.Chat
import com.dmitrysimakov.kilogram.data.model.Message
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.remote.firestore
import com.dmitrysimakov.kilogram.data.remote.uid
import com.dmitrysimakov.kilogram.data.remote.usersCollection
import com.dmitrysimakov.kilogram.util.live_data.liveData
import kotlinx.coroutines.tasks.await

class MessagesSource {
    
    suspend fun chat(user: User, companionId: String): Chat {
        val userChatRef      = firestore.document("users/$uid/chats/$companionId")
        val companionChatRef = firestore.document("users/$companionId/chats/$uid")
    
        val chat: Chat
        val userChatDoc = userChatRef.get().await()
        if (userChatDoc.exists()) {
            chat = userChatDoc.toObject(Chat::class.java)!!
        } else {
            val companionDoc = usersCollection.document(companionId).get().await()
            val companion = companionDoc.toObject(User::class.java)!!
            val newChatForUser = Chat(companion.id, companion)
            val newChatForCompanion = Chat(user.id, user)
            chat = newChatForUser
            
            firestore.batch()
                    .set(userChatRef, newChatForUser)
                    .set(companionChatRef, newChatForCompanion)
                    .commit()
        }
        
        return chat
    }
    
    fun messagesLive(companionId: String): LiveData<List<Message>> {
        return firestore.collection("users/$uid/chats/$companionId/messages")
                .orderBy("timestamp")
                .liveData { it.toObject(Message::class.java)!! }
    }
    
    fun sendMessage(message: Message, receiverId: String) {
        val senderChatRef      = firestore.document("users/$uid/chats/$receiverId")
        val receiverChatRef    = firestore.document("users/$receiverId/chats/$uid")
        val senderMessageRef   = firestore.document("users/$uid/chats/$receiverId/messages/${message.id}")
        val receiverMessageRef = firestore.document("users/$receiverId/chats/$uid/messages/${message.id}")
        firestore.batch()
                .set(senderMessageRef, message)
                .set(receiverMessageRef, message)
                .update(senderChatRef, "lastMessage", message)
                .update(receiverChatRef, "lastMessage", message)
                .commit()
    }
    
    fun markNewMessagesAsRead(messages: List<Message>, companionId: String) {
        val userChatRef         = firestore.document("users/$uid/chats/$companionId")
        val companionChatRef    = firestore.document("users/$companionId/chats/$uid")
        val userMessagesRef      = firestore.collection("users/$uid/chats/$companionId/messages")
        val companionMessagesRef = firestore.collection("users/$companionId/chats/$uid/messages")
        
        val writeBatch = firestore.batch()
        var newMessagesReceived = false
        
        messages.forEach { msg ->
            if (msg.senderId != uid && !msg.wasRead) {
                newMessagesReceived = true
                writeBatch
                        .update(userMessagesRef.document(msg.id), "wasRead", true)
                        .update(companionMessagesRef.document(msg.id), "wasRead", true)
            }
        }
        if (newMessagesReceived) {
            writeBatch
                    .update(userChatRef, "lastMessage.wasRead", true)
                    .update(companionChatRef, "lastMessage.wasRead", true)
        }
        writeBatch.commit()
    }
}
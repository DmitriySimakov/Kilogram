package com.dmitrysimakov.kilogram.ui.subscriptions.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.dmitrysimakov.kilogram.data.remote.Chat
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.util.chatsCollection
import com.dmitrysimakov.kilogram.util.firebaseUser
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.userDocument
import com.dmitrysimakov.kilogram.util.usersCollection
import com.google.firebase.firestore.SetOptions

class PeopleViewModel : ViewModel() {
    
    val people = usersCollection.liveData { doc ->
        doc.toObject(User::class.java)!!.also { user -> user.id = doc.id }
    }.map { it.filter { user -> user.id != firebaseUser!!.uid } }
    
    fun getChatWith(user: User, callback: ((String) -> Unit)) {
        val curUserDirectsDocument = userDocument.collection("chats").document("chats")
        curUserDirectsDocument.get().addOnSuccessListener { doc ->
            val chatId = doc.get(user.id) as String?
            if (chatId == null) {
                chatsCollection.add(Chat(
                        listOf(Chat.Member(firebaseUser!!.uid, firebaseUser!!.displayName ?: "",
                                        firebaseUser!!.photoUrl.toString()),
                                Chat.Member(user.id, user.name, user.photoUrl)),
                        listOf(firebaseUser!!.uid, user.id))
                ).addOnSuccessListener { chatDoc ->
                    curUserDirectsDocument.set(hashMapOf(user.id to chatDoc.id), SetOptions.merge())
                    callback(chatDoc.id)
                }
            } else {
                callback(chatId)
            }
        }
    }
}
package com.dmitrysimakov.kilogram.ui.subscriptions.people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.remote.Chat
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.data.remote.toUser
import com.dmitrysimakov.kilogram.util.chatsCollection
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.userDocument
import com.dmitrysimakov.kilogram.util.usersCollection
import com.google.firebase.firestore.SetOptions

class PeopleViewModel : ViewModel() {
    
    private val _user = MutableLiveData<User>()
    
    val people = _user.switchMap { currentUser ->
        usersCollection.liveData { it.toUser() }.map {
            it.filter { user -> user?.id != currentUser.id }
        }
    }
    
    fun setUser(user: User) { _user.setNewValue(user) }
    
    fun getChatWith(user: User, callback: ((String) -> Unit)) {
        val curUserDirectsDocument = userDocument.collection("chats").document("chats")
        curUserDirectsDocument.get().addOnSuccessListener { doc ->
            val chatId = doc.get(user.id) as String?
            if (chatId == null) {
                _user.value?.let { curUser ->
                    val currentMember = Chat.Member(curUser.id, curUser.name, curUser.photoUrl.toString())
                    val members = listOf(currentMember, Chat.Member(user.id, user.name, user.photoUrl))
                    val membersIds = listOf(curUser.id, user.id)
                    chatsCollection.add(Chat(members, membersIds)).addOnSuccessListener { chatDoc ->
                        curUserDirectsDocument.set(hashMapOf(user.id to chatDoc.id), SetOptions.merge())
                        callback(chatDoc.id)
                    }
                }
            } else {
                callback(chatId)
            }
        }
    }
}
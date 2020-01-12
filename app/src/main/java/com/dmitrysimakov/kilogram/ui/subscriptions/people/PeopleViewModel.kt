package com.dmitrysimakov.kilogram.ui.subscriptions.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    
    private val _searchText = MutableLiveData<String?>(null)
    val searchText: LiveData<String?> = _searchText
    
    private val _user = MutableLiveData<User?>()
    
    private val _people = usersCollection.liveData { it.toUser()!! }
    val people = MediatorLiveData<List<User>>()
    
    init {
        listOf(_people, _user, _searchText).forEach { people.addSource(it) { filterPeople() } }
    }
    
    private fun filterPeople() {
        val loadedPeople = _people.value
        val user = _user.value
        val searchText = _searchText.value
        
        if (loadedPeople == null || user == null) {
            people.value = null
            return
        }
        
        people.value = loadedPeople.filter { person ->
            var isValidName = true
            
            searchText?.let {
                val nameParts = searchText.trim().split(" ")
                nameParts.forEach {
                    if (!person.name.contains(it)) isValidName = false
                }
            }
    
            person.id != user.id && isValidName
        }
    }
    
    fun setUser(user: User?) { _user.setNewValue(user) }
    
    fun setSearchText(text: String?) { _searchText.setNewValue(text) }
    
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
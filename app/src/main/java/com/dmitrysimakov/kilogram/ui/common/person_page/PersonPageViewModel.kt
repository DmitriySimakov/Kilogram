package com.dmitrysimakov.kilogram.ui.common.person_page

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.model.Post
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.remote.data_sources.PostSource
import com.dmitrysimakov.kilogram.data.remote.firestore
import com.dmitrysimakov.kilogram.data.remote.userDocument
import com.dmitrysimakov.kilogram.data.remote.usersCollection
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PersonPageViewModel(private val postSrc: PostSource) : ViewModel() {
    
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user
    
    private val _personId = MutableLiveData<String>()
    val person = _personId.switchMap { id -> liveData {
        emit(usersCollection.document(id).get().await().toObject(User::class.java)!!)
    }}
    
    private val _subscribed = MutableLiveData<Boolean>()
    val subscribed: LiveData<Boolean> = _subscribed
    
    val posts = _personId.switchMap { postSrc.postsLive(it) }
    
    fun start(user: User?, personId: String) {
        _user.setNewValue(user)
        _personId.setNewValue(personId)
        val subscribed = user?.subscriptions?.contains(personId)
        _subscribed.setNewValue(subscribed)
    }
    
    fun updateSubscriptions() { viewModelScope.launch {
        val user = _user.value ?: return@launch
        val person = person.value!!
        
        val userSubscriptions = user.subscriptions.toMutableList()
        val personSubscribers = person.subscribers.toMutableList()
        
        if (subscribed.value!!) {
            userSubscriptions.remove(person.id)
            personSubscribers.remove(user.id)
        } else {
            userSubscriptions.add(person.id)
            personSubscribers.add(user.id)
        }
        
        firestore.batch()
                .update(userDocument, "subscriptions", userSubscriptions)
                .update(usersCollection.document(person.id), "subscribers", personSubscribers)
                .commit()
    }}
    
    fun likePost(post: Post) { postSrc.likePost(post) }
}
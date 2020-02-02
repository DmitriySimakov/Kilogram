package com.dmitrysimakov.kilogram.ui.common.person_page

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.util.firestore
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.userDocument
import com.dmitrysimakov.kilogram.util.usersCollection
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PersonPageViewModel : ViewModel() {
    
    private val _user = MutableLiveData<User?>()
    
    private val _personId = MutableLiveData<String>()
    val person = _personId.switchMap { id -> liveData {
        emit(usersCollection.document(id).get().await().toObject(User::class.java)!!)
    }}
    
    private val _subscribed = MutableLiveData<Boolean>()
    val subscribed: LiveData<Boolean> = _subscribed
    
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
}
package com.dmitrysimakov.kilogram.ui.common.person_page.subscribers

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.remote.usersCollection
import com.dmitrysimakov.kilogram.util.live_data.liveData
import kotlinx.coroutines.tasks.await

class SubscribersViewModel : ViewModel() {
    
    val userId = MutableLiveData<String>()
    
    val user = userId.switchMap { id -> liveData {
        val snapshot = usersCollection.document(id).get().await()
        emit(snapshot.toObject(User::class.java)!!)
    }}
    
    val subscribers = user.switchMap { user ->
        usersCollection.liveData { it.toObject(User::class.java)!! }.map {
            it.filter {person -> user.subscribers.contains(person.id) }
        }
    }
}
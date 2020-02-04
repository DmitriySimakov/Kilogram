package com.dmitrysimakov.kilogram.ui.person_page.subscriptions

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.usersCollection
import kotlinx.coroutines.tasks.await

class SubscriptionsViewModel : ViewModel() {
    
    val userId = MutableLiveData<String>()
    
    val user = userId.switchMap { id -> liveData {
        val snapshot = usersCollection.document(id).get().await()
        emit(snapshot.toObject(User::class.java)!!)
    }}
    
    val subscriptions = user.switchMap { user ->
        usersCollection.liveData { it.toObject(User::class.java)!! }.map {
            it.filter {person -> user.subscriptions.contains(person.id) }
        }
    }
}
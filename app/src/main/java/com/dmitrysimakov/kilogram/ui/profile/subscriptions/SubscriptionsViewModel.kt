package com.dmitrysimakov.kilogram.ui.profile.subscriptions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.util.live_data.AbsentLiveData
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.usersCollection

class SubscriptionsViewModel : ViewModel() {
    
    private val _user = MutableLiveData<User?>()
    
    val subscriptions = _user.switchMap { user ->
        if (user == null) AbsentLiveData.create()
        else usersCollection.liveData { it.toObject(User::class.java)!! }.map {
            it.filter {person -> user.subscriptions.contains(person.id) }
        }
    }
    
    fun setUser(user: User?) { _user.setNewValue(user) }
}
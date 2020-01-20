package com.dmitrysimakov.kilogram.ui.subscriptions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.remote.Subscriptions
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.data.remote.toPost
import com.dmitrysimakov.kilogram.util.live_data.AbsentLiveData
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.postsCollection
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.subscriptionsDocument

class SubscriptionsViewModel : ViewModel() {
    
    private val _user = MutableLiveData<User?>()
    
    private val _subscriptions = _user.switchMap { user ->
        if (user == null) AbsentLiveData.create()
        else subscriptionsDocument(user.id).liveData { it.toObject(Subscriptions::class.java) }
    }
    
    val posts = _subscriptions.switchMap { subscriptions ->
        if (subscriptions == null) AbsentLiveData.create()
        else postsCollection.liveData { it.toPost() }.map {
            it.filter { post -> subscriptions.followedIds.contains(post.authorId) }
        }
    }
    
    fun setUser(user: User?) { _user.setNewValue(user) }
}
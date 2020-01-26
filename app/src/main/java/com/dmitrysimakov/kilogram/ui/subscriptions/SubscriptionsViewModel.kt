package com.dmitrysimakov.kilogram.ui.subscriptions

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.remote.models.Post
import com.dmitrysimakov.kilogram.data.remote.models.Subscriptions
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.util.live_data.AbsentLiveData
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.postsCollection
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.subscriptionsDocument

class SubscriptionsViewModel : ViewModel() {
    
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user
    
    private val _subscriptions = _user.switchMap { user ->
        if (user == null) AbsentLiveData.create()
        else subscriptionsDocument.liveData { it.toObject(Subscriptions::class.java) }
    }
    
    val posts = _subscriptions.switchMap { subscriptions ->
        if (subscriptions == null) AbsentLiveData.create()
        else postsCollection.liveData { it.toObject(Post::class.java)!! }.map {
            it.filter { post -> subscriptions.followedIds.contains(post.authorId) }
        }
    }
    
    fun setUser(user: User?) { _user.setNewValue(user) }
    
    fun like(post: Post?) {
        val userId = user.value?.id ?: return
        val likes = post?.likes?.toMutableList() ?: return
        if (likes.contains(userId)) likes.remove(userId) else likes.add(userId)
        postsCollection.document(post.id).update("likes", likes)
    }
}
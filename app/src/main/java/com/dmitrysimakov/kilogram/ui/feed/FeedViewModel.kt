package com.dmitrysimakov.kilogram.ui.feed

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.remote.models.Post
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.util.live_data.AbsentLiveData
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.postsCollection
import com.dmitrysimakov.kilogram.util.setNewValue

class FeedViewModel : ViewModel() {
    
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user
    
    val posts = _user.switchMap { user ->
        if (user == null) AbsentLiveData.create()
        else postsCollection.liveData { it.toObject(Post::class.java)!! }.map {
            it.filter { post -> user.subscriptions.contains(post.authorId) }
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
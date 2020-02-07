package com.dmitrysimakov.kilogram.ui.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.model.Post
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.remote.postsCollection
import com.dmitrysimakov.kilogram.util.live_data.AbsentLiveData
import com.dmitrysimakov.kilogram.util.live_data.liveData

class FeedViewModel : ViewModel() {
    
    val user = MutableLiveData<User?>()
    
    val posts = user.switchMap { user ->
        if (user == null) AbsentLiveData.create()
        else postsCollection.liveData { it.toObject(Post::class.java)!! }.map {
            it.filter { post -> user.subscriptions.contains(post.authorId) }
        }
    }
    
    fun like(post: Post?) {
        val userId = user.value?.id ?: return
        val likes = post?.likes?.toMutableList() ?: return
        if (likes.contains(userId)) likes.remove(userId) else likes.add(userId)
        postsCollection.document(post.id).update("likes", likes)
    }
}
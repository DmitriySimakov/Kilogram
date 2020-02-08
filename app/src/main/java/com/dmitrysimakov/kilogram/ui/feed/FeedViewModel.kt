package com.dmitrysimakov.kilogram.ui.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.model.Post
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.remote.data_sources.PostSource
import com.dmitrysimakov.kilogram.util.live_data.AbsentLiveData

class FeedViewModel(private val postSrc: PostSource) : ViewModel() {
    
    val user = MutableLiveData<User?>()
    
    val posts = user.switchMap { user ->
        if (user == null) AbsentLiveData.create()
        else postSrc.postsLive().map { it.filter { post ->
            user.subscriptions.contains(post.authorId)
        }}
    }
    
    fun like(post: Post) {
        user.value ?: return
        postSrc.likePost(post)
    }
}
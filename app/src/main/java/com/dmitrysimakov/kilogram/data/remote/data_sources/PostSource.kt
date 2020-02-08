package com.dmitrysimakov.kilogram.data.remote.data_sources

import com.dmitrysimakov.kilogram.data.model.Post
import com.dmitrysimakov.kilogram.data.remote.firestore
import com.dmitrysimakov.kilogram.data.remote.uid
import com.dmitrysimakov.kilogram.util.live_data.liveData

class PostSource {
    
    private val postsCollection = firestore.collection("posts")
    
    fun postsLive() = postsCollection.liveData { it.toObject(Post::class.java)!! }
    
    fun publishPost(post: Post) {
        postsCollection.document(post.id).set(post)
    }
    
    fun likePost(post: Post) {
        val likes = post.likes.toMutableList()
        if (likes.contains(uid)) likes.remove(uid) else likes.add(uid)
        postsCollection.document(post.id).update("likes", likes)
    }
}
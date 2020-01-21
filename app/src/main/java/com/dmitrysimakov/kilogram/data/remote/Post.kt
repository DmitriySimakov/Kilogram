package com.dmitrysimakov.kilogram.data.remote

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Post(
        val authorId: String? = null,
        val authorName: String? = null,
        val authorPhotoUrl: String? = null,
        val title: String? = null,
        val content: String? = null,
        val imageUrl: String? = null,
        val timestamp: Date = Date(),
        val likes: List<String> = listOf(), // Ids of people who liked the post
        @get:Exclude var id: String = ""
)

fun DocumentSnapshot.toPost() = toObject(Post::class.java)!!.also { it.id = id }
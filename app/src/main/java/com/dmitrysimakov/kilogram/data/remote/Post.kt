package com.dmitrysimakov.kilogram.data.remote

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Post(
        var authorId: String? = null,
        var authorName: String? = null,
        var authorPhotoUrl: String? = null,
        var title: String? = null,
        var content: String? = null,
        var imageUrl: String? = null,
        var timestamp: Date = Date(),
        var likesCount: Int = 0,
        @get:Exclude var id: String = ""
)

fun DocumentSnapshot.toPost() = toObject(Post::class.java)!!.also { it.id = id }
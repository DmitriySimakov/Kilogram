package com.dmitrysimakov.kilogram.data.remote.models

import java.util.*

data class Post(
        val id: String = "",
        val authorId: String? = null,
        val authorName: String? = null,
        val authorPhotoUrl: String? = null,
        val title: String? = null,
        val content: String? = null,
        val imageUrl: String? = null,
        val programId: String? = null,
        val timestamp: Date = Date(),
        val likes: List<String> = listOf() // Ids of people who liked the post
)
package com.dmitrysimakov.kilogram.data.model

import com.dmitrysimakov.kilogram.data.remote.generateId
import java.util.*

data class Post(
        val authorId: String? = null,
        val authorName: String? = null,
        val authorPhotoUrl: String? = null,
        val title: String? = null,
        val content: String? = null,
        val imageUrl: String? = null,
        val programId: String? = null,
        val timestamp: Date = Date(),
        val likes: List<String> = listOf(), // Ids of people who liked the post
        val id: String = generateId()
)
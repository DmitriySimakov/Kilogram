package com.dmitrysimakov.kilogram.data.remote.models

import java.util.*

data class Message(
        val id: String = "",
        val senderId: String = "",
        val text: String? = null,
        val imageUrl: String? = null,
        val timestamp: Date = Date(),
        val wasRead: Boolean = false
)
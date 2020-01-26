package com.dmitrysimakov.kilogram.data.remote.models

import java.util.*

data class Photo(
        val uri: String = "",
        val dateTime: Date = Date(),
        val updatedAt: Date = Date()
)
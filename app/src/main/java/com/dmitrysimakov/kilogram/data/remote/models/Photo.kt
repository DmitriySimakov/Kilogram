package com.dmitrysimakov.kilogram.data.remote.models

import java.util.*

data class Photo(
        val uri: String = "",
        val dateTime: String = "",
        val lastUpdate: Date = Date(),
        val deleted: Boolean = false
)
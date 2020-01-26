package com.dmitrysimakov.kilogram.data.remote.models

import java.util.*

data class Training(
        val id: Long = 0,
        val startDateTime: Date = Date(),
        val duration: Int? = null,
        val programDayId: Long? = null,
        val updatedAt: Date = Date()
)
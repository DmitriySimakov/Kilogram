package com.dmitrysimakov.kilogram.data.remote.models

import java.util.*

data class Training(
        val id: Long = 0,
        val startDateTime: String = "",
        val duration: Int? = null,
        val programDayId: Long? = null,
        val lastUpdate: Date = Date(),
        val deleted: Boolean = false
)
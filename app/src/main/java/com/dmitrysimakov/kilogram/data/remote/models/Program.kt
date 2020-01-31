package com.dmitrysimakov.kilogram.data.remote.models

import java.util.*

data class Program(
        val id: Long = 0,
        val name: String = "",
        val description: String = "",
        val lastUpdate: Date = Date(),
        val deleted: Boolean = false
)
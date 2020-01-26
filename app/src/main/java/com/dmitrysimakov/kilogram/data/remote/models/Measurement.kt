package com.dmitrysimakov.kilogram.data.remote.models

import java.util.*

data class Measurement(
        val id: Long = 0,
        val date: Date = Date(),
        val param: String = "",
        val value: Double = 0.0,
        val updatedAt: Date = Date()
)
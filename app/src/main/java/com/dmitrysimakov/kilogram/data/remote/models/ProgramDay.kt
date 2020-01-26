package com.dmitrysimakov.kilogram.data.remote.models

import java.util.*

data class ProgramDay(
        val id: Long = 0,
        val programId: Long = 0,
        var indexNumber: Int = 1,
        val name: String = "",
        val description: String = "",
        val updatedAt: Date = Date()
)
package com.dmitrysimakov.kilogram.data.remote.models

import java.util.*

data class ProgramDayExercise(
        val id: Long = 0,
        val programDayId: Long = 0,
        val exercise: String = "",
        var indexNumber: Int = 1,
        val rest: Int = 120,
        val strategy: String? = null,
        val lastUpdate: Date = Date()
)
package com.dmitrysimakov.kilogram.data.remote.models

import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise.Companion.PLANNED
import java.util.*

data class TrainingExercise(
        val id: Long = 0,
        val trainingId: Long = 0,
        val exercise: String = "",
        val indexNumber: Int = 1,
        val rest: Int = 120,
        val strategy: String? = null,
        val state: Int = PLANNED,
        val updatedAt: Date = Date()
)
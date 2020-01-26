package com.dmitrysimakov.kilogram.data.remote.models

import java.util.*

data class TrainingSet(
        val id: Long = 0,
        val trainingExerciseId: Long = 0,
        val weight: Int? = null,
        val reps: Int? = null,
        val time: Int? = null,
        val distance: Int? = null,
        val updatedAt: Date = Date()
)
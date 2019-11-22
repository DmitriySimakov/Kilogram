package com.dmitrysimakov.kilogram.data.local.relation

import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise

data class DetailedTrainingExercise(
        val _id: Long,
        val exercise: String,
        var indexNumber: Int,
        val rest: Int,
        val secs_since_start: Int = 0,
        val strategy: String? = null,
        val state: Int = TrainingExercise.PLANNED
)
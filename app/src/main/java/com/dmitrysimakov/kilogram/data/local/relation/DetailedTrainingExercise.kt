package com.dmitrysimakov.kilogram.data.local.relation

import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise

data class DetailedTrainingExercise(
        val id: Long,
        val exercise: String,
        var indexNumber: Int,
        val rest: Int,
        val strategy: String? = null,
        val state: Int = TrainingExercise.PLANNED
)
package com.dmitrysimakov.kilogram.data.relation

import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise

data class TrainingExerciseR(
        val _id: Long,
        val exercise_id: Long,
        val name: String,
        var num: Int,
        val rest: Int,
        val secs_since_start: Int,
        val strategy: String?,
        val state: Int = TrainingExercise.PLANNED
)
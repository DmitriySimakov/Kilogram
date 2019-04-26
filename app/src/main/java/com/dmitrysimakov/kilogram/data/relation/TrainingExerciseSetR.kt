package com.dmitrysimakov.kilogram.data.relation

data class TrainingExerciseSetR(
        val _id: Long,
        val weight: Int?,
        val reps: Int?,
        val time: Int?,
        val distance: Int?,
        val prev_id: Long,
        val prev_weight: Int?,
        val prev_reps: Int?,
        val prev_time: Int?,
        val prev_distance: Int?
)
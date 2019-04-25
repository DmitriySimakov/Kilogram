package com.dmitrysimakov.kilogram.data.relation

data class DetailedExerciseR (
        val _id: Long,
        val name: String,
        val description: String? = null,
        var is_favorite: Boolean = false,
        val main_muscle: String? = null,
        val targeted_muscles: String? = null,
        val mechanics_type: String? = null,
        val exercise_type: String? = null,
        val equipment: String? = null
)
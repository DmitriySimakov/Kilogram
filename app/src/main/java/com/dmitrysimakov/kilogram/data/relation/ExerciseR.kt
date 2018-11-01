package com.dmitrysimakov.kilogram.data.relation

class ExerciseR (
        val name: String,
        val description: String? = null,
        val main_muscle: String? = null,
        val targeted_muscles: String? = null,
        val mechanics_type: String? = null,
        val exercise_type: String? = null,
        val equipment: String? = null
)
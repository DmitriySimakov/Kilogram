package com.dmitrysimakov.kilogram.data.relation

import com.dmitrysimakov.kilogram.util.HasId

data class TrainingExerciseR(
        override val _id: Long,
        val exercise_id: Long,
        val name: String,
        var num: Int,
        val rest: Int,
        val strategy: String?
) : HasId
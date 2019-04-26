package com.dmitrysimakov.kilogram.data.relation

data class ProgramExerciseR(
        val _id: Long,
        val exercise_id: Long,
        val name: String,
        var num: Int,
        val rest: Int,
        val strategy: String?
)
package com.dmitrysimakov.kilogram.data.local.relation

data class SetWithPreviousResults(
        val id: Long,
        val weight: Int?,
        val reps: Int?,
        val time: Int?,
        val distance: Int?,
        val prevId: Long,
        val prevWeight: Int?,
        val prevReps: Int?,
        val prevTime: Int?,
        val prevDistance: Int?
)
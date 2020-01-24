package com.dmitrysimakov.kilogram.data.local.relation

import org.threeten.bp.OffsetDateTime

data class TrainingExerciseInfo(
        val trainingExerciseId: Long,
        val trainingId: Long?,
        val startDateTime: OffsetDateTime?
)
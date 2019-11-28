package com.dmitrysimakov.kilogram.data.local.relation

import org.threeten.bp.OffsetDateTime

data class TrainingExerciseInfo(
        val training_exercise_id: Long,
        val training_id: Long?,
        val start_date_time: OffsetDateTime?
)
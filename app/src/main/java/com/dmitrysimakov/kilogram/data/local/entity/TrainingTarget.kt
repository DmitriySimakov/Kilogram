package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "training_target",
        indices = [Index(value = ["exercise_target"])],
        primaryKeys = ["training_id", "exercise_target"],
        foreignKeys = [
            ForeignKey(
                    entity = Training::class,
                    parentColumns = ["_id"],
                    childColumns = ["training_id"],
                    onDelete = ForeignKey.CASCADE),
            ForeignKey(
                    entity = ExerciseTarget::class,
                    parentColumns = ["name"],
                    childColumns = ["exercise_target"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class TrainingTarget(val training_id: Long, val exercise_target: String)
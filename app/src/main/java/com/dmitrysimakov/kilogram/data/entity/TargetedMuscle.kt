package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "targeted_muscle",
        indices = [Index(value = ["muscle_id"])],
        primaryKeys = ["exercise_id", "muscle_id"],
        foreignKeys = [
            ForeignKey(
                    entity = Exercise::class,
                    parentColumns = ["_id"],
                    childColumns = ["exercise_id"],
                    onDelete = ForeignKey.CASCADE),
            ForeignKey(
                    entity = Muscle::class,
                    parentColumns = ["_id"],
                    childColumns = ["muscle_id"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class TargetedMuscle(val exercise_id: Long, val muscle_id: Long)
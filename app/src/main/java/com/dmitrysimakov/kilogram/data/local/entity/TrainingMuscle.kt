package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "training_muscle",
        indices = [Index(value = ["muscle"])],
        primaryKeys = ["training_id", "muscle"],
        foreignKeys = [
            ForeignKey(
                    entity = Training::class,
                    parentColumns = ["_id"],
                    childColumns = ["training_id"],
                    onDelete = ForeignKey.CASCADE),
            ForeignKey(
                    entity = Muscle::class,
                    parentColumns = ["name"],
                    childColumns = ["muscle"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class TrainingMuscle(val training_id: Long, val muscle: String)
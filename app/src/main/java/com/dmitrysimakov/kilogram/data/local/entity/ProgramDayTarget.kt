package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "program_day_targets",
        indices = [Index(value = ["exercise_target"])],
        primaryKeys = ["program_day_id", "exercise_target"],
        foreignKeys = [
            ForeignKey(
                    entity = ProgramDay::class,
                    parentColumns = ["_id"],
                    childColumns = ["program_day_id"],
                    onDelete = ForeignKey.CASCADE),
            ForeignKey(
                    entity = ExerciseTarget::class,
                    parentColumns = ["name"],
                    childColumns = ["exercise_target"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class ProgramDayTarget(val program_day_id: Long, val exercise_target: String)
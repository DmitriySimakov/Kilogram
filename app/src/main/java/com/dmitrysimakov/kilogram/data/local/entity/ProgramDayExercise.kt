package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "program_day_exercise",
        indices = [Index(value = ["program_day_id"]), Index(value = ["exercise"])],
        foreignKeys = [
            ForeignKey(
                    entity = ProgramDay::class,
                    parentColumns = ["_id"],
                    childColumns = ["program_day_id"],
                    onDelete = ForeignKey.CASCADE),
            ForeignKey(
                    entity = Exercise::class,
                    parentColumns = ["name"],
                    childColumns = ["exercise"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class ProgramDayExercise(
        @PrimaryKey(autoGenerate = true) val _id: Long = 0,
        val program_day_id: Long,
        val exercise: String,
        var indexNumber: Int,
        val rest: Int = 120,
        val strategy: String? = null
)
package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["programDayId"]), Index(value = ["exercise"])],
        foreignKeys = [
            ForeignKey(
                    entity = ProgramDay::class,
                    parentColumns = ["id"],
                    childColumns = ["programDayId"],
                    onDelete = ForeignKey.CASCADE),
            ForeignKey(
                    entity = Exercise::class,
                    parentColumns = ["name"],
                    childColumns = ["exercise"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class ProgramDayExercise(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val programDayId: Long,
        val exercise: String,
        var indexNumber: Int,
        val rest: Int = 120,
        val strategy: String? = null
)
package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "program_day_muscle",
        indices = [Index(value = ["muscle"])],
        primaryKeys = ["program_day_id", "muscle"],
        foreignKeys = [
            ForeignKey(
                    entity = ProgramDay::class,
                    parentColumns = ["_id"],
                    childColumns = ["program_day_id"],
                    onDelete = ForeignKey.CASCADE),
            ForeignKey(
                    entity = Muscle::class,
                    parentColumns = ["name"],
                    childColumns = ["muscle"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class ProgramDayMuscle(val program_day_id: Long, val muscle: String)
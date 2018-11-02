package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.util.HasId

@Entity(tableName = "program_day_exercise",
        foreignKeys = [
    ForeignKey(
            entity = ProgramDay::class,
            parentColumns = ["_id"],
            childColumns = ["program_day_id"],
            onDelete = ForeignKey.CASCADE),
    ForeignKey(
            entity = Exercise::class,
            parentColumns = ["_id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE)
])
data class ProgramDayExercise(
        @PrimaryKey(autoGenerate = true) override val _id: Long = 0,
        val program_day_id: Long,
        val exercise_id: Long,
        val number: Byte,
        val params_bool_arr: Short? = null,
        val strategy: String? = null
) : HasId
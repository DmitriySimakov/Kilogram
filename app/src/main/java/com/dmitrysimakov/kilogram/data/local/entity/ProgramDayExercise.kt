package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.*
import com.dmitrysimakov.kilogram.data.relation.ExerciseMeasures

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
        val rest: Int,
        val strategy: String? = null,
        @Embedded(prefix = "measure_") val measures: ExerciseMeasures = ExerciseMeasures()
)
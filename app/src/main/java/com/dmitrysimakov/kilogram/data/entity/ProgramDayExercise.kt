package com.dmitrysimakov.kilogram.data.entity

import androidx.room.*
import com.dmitrysimakov.kilogram.data.relation.ExerciseMeasures
import com.dmitrysimakov.kilogram.util.HasId

@Entity(tableName = "program_day_exercise",
        indices = [Index(value = ["program_day_id"]), Index(value = ["exercise_id"])],
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
        ]
)
data class ProgramDayExercise(
        @PrimaryKey(autoGenerate = true) override val _id: Long = 0,
        val program_day_id: Long,
        val exercise_id: Long,
        val num: Int,
        val rest: Int,
        val strategy: String? = null,
        @Embedded(prefix = "measure_") val measures: ExerciseMeasures = ExerciseMeasures()
) : HasId
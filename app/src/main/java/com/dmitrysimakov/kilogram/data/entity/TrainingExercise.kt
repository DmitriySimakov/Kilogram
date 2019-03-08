package com.dmitrysimakov.kilogram.data.entity

import androidx.room.*
import com.dmitrysimakov.kilogram.data.relation.ExerciseMeasures
import com.dmitrysimakov.kilogram.util.HasId

@Entity(tableName = "training_exercise",
        foreignKeys = [
            ForeignKey(
                    entity = Training::class,
                    parentColumns = ["_id"],
                    childColumns = ["training_id"],
                    onDelete = ForeignKey.CASCADE),
            ForeignKey(
                    entity = Exercise::class,
                    parentColumns = ["_id"],
                    childColumns = ["exercise_id"],
                    onDelete = ForeignKey.CASCADE)
        ])
data class TrainingExercise(
        @PrimaryKey(autoGenerate = true) override val _id: Long = 0,
        val training_id: Long,
        val exercise_id: Long,
        val num: Int,
        val rest: Int,
        val strategy: String? = null,
        @Embedded(prefix = "measure_") val measures: ExerciseMeasures = ExerciseMeasures()
) : HasId
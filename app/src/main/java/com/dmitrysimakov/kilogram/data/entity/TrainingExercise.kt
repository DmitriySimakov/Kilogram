package com.dmitrysimakov.kilogram.data.entity

import androidx.room.*
import com.dmitrysimakov.kilogram.data.relation.ExerciseMeasures

@Entity(tableName = "training_exercise",
        indices = [Index(value = ["training_id"]), Index(value = ["exercise_id"])],
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
        @PrimaryKey(autoGenerate = true) val _id: Long = 0,
        val training_id: Long,
        val exercise_id: Long,
        val num: Int,
        val rest: Int,
        val strategy: String? = null,
        val state: Int = PLANNED,
        @Embedded(prefix = "measure_") val measures: ExerciseMeasures = ExerciseMeasures()
) {
    companion object {
        const val PLANNED = 0
        const val RUNNING = 1
        const val FINISHED = 2
    }
}
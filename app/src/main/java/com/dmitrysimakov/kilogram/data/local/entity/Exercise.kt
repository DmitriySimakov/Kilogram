package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.*
import com.dmitrysimakov.kilogram.data.relation.ExerciseMeasures

@Entity(tableName = "exercise",
        indices = [Index(value = ["main_muscle_id"]), Index(value = ["mechanics_type_id"]), Index(value = ["exercise_type_id"]), Index(value = ["equipment_id"])],
        foreignKeys = [
            ForeignKey(
                    entity = Muscle::class,
                    parentColumns = ["_id"],
                    childColumns = ["main_muscle_id"],
                    onDelete = ForeignKey.SET_NULL),
            ForeignKey(
                    entity = MechanicsType::class,
                    parentColumns = ["_id"],
                    childColumns = ["mechanics_type_id"],
                    onDelete = ForeignKey.SET_NULL),
            ForeignKey(
                    entity = ExerciseType::class,
                    parentColumns = ["_id"],
                    childColumns = ["exercise_type_id"],
                    onDelete = ForeignKey.SET_NULL),
            ForeignKey(
                    entity = Equipment::class,
                    parentColumns = ["_id"],
                    childColumns = ["equipment_id"],
                    onDelete = ForeignKey.SET_NULL)
        ]
)
data class Exercise(
        @PrimaryKey(autoGenerate = true) val _id: Long = 0,
        val name: String,
        val main_muscle_id: Long? = null,
        val mechanics_type_id: Long? = null,
        val exercise_type_id: Long? = null,
        val equipment_id: Long? = null,
        val description: String? = null,
        val executions_cnt: Long = 0,
        var is_favorite: Boolean = false,
        @Embedded(prefix = "measure_") val measures: ExerciseMeasures = ExerciseMeasures()
)
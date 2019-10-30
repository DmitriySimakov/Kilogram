package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.*
import com.dmitrysimakov.kilogram.data.relation.ExerciseMeasures

@Entity(tableName = "exercise",
        indices = [
            Index(value = ["main_muscle"]),
            Index(value = ["mechanics_type"]),
            Index(value = ["exercise_type"]),
            Index(value = ["equipment"])
        ],
        foreignKeys = [
            ForeignKey(
                    entity = Muscle::class,
                    parentColumns = ["name"],
                    childColumns = ["main_muscle"],
                    onDelete = ForeignKey.SET_NULL),
            ForeignKey(
                    entity = MechanicsType::class,
                    parentColumns = ["name"],
                    childColumns = ["mechanics_type"],
                    onDelete = ForeignKey.SET_NULL),
            ForeignKey(
                    entity = ExerciseType::class,
                    parentColumns = ["name"],
                    childColumns = ["exercise_type"],
                    onDelete = ForeignKey.SET_NULL),
            ForeignKey(
                    entity = Equipment::class,
                    parentColumns = ["name"],
                    childColumns = ["equipment"],
                    onDelete = ForeignKey.SET_NULL)
        ]
)
data class Exercise(
        @PrimaryKey val name: String,
        val main_muscle: String? = null,
        val mechanics_type: String? = null,
        val exercise_type: String? = null,
        val equipment: String? = null,
        val description: String = "",
        val executions_cnt: Long = 0,
        var is_favorite: Boolean = false,
        @Embedded(prefix = "measure_") val measures: ExerciseMeasures = ExerciseMeasures()
)
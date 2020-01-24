package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.relation.ExerciseMeasures

@Entity(indices = [
            Index(value = ["target"]),
            Index(value = ["equipment"])
        ],
        foreignKeys = [
            ForeignKey(
                    entity = ExerciseTarget::class,
                    parentColumns = ["name"],
                    childColumns = ["target"],
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
        val target: String? = null,
        val isIsolated: Boolean? = null,
        val equipment: String? = null,
        val description: String = "",
        val executionsCount: Long = 0,
        var isFavorite: Boolean = false,
        @Embedded(prefix = "measure_") val measures: ExerciseMeasures = ExerciseMeasures()
)
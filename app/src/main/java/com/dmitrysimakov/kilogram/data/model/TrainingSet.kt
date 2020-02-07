package com.dmitrysimakov.kilogram.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.data.remote.generateId
import java.util.*

@Entity(indices = [Index(value = ["trainingExerciseId"])],
        foreignKeys = [
            ForeignKey(
                    entity = TrainingExercise::class,
                    parentColumns = ["id"],
                    childColumns = ["trainingExerciseId"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class TrainingSet(
        val trainingExerciseId: String = "",
        val weight: Int? = null,
        val reps: Int? = null,
        val time: Int? = null,
        val distance: Int? = null,
        val lastUpdate: Date = Date(),
        val deleted: Boolean = false,
        @PrimaryKey val id: String = generateId()
)
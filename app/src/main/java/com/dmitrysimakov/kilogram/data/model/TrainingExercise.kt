package com.dmitrysimakov.kilogram.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.util.generateId
import java.util.*

@Entity(indices = [Index(value = ["trainingId"]), Index(value = ["exercise"])],
        foreignKeys = [
            ForeignKey(
                    entity = Training::class,
                    parentColumns = ["id"],
                    childColumns = ["trainingId"],
                    onDelete = ForeignKey.CASCADE),
            ForeignKey(
                    entity = Exercise::class,
                    parentColumns = ["name"],
                    childColumns = ["exercise"],
                    onDelete = ForeignKey.CASCADE)
        ])
data class TrainingExercise(
        val trainingId: String = "",
        val exercise: String = "",
        var indexNumber: Int = 0,
        val rest: Int = 120,
        val strategy: String? = null,
        val state: Int = PLANNED,
        val lastUpdate: Date = Date(),
        val deleted: Boolean = false,
        @PrimaryKey val id: String = generateId()
) {
    companion object {
        const val PLANNED = 0
        const val RUNNING = 1
        const val FINISHED = 2
    }
}
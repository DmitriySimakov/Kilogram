package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
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
        val training_id: Long,
        val exercise_id: Long,
        val number: Byte,
        val params_bool_arr: Short? = null
) : HasId {
    @PrimaryKey(autoGenerate = true) override var _id: Long = 0
}
package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.util.HasId

@Entity(tableName = "exercise",
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
        val name: String,
        val main_muscle_id: Long? = null,
        val mechanics_type_id: Long? = null,
        val exercise_type_id: Long? = null,
        val equipment_id: Long? = null,
        val description: String? = null
) : HasId {
    @PrimaryKey(autoGenerate = true) override var _id: Long = 0
}
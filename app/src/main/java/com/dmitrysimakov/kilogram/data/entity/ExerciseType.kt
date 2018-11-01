package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.util.HasId

@Entity(tableName = "exercise_type")
data class ExerciseType(val name: String) : HasId {
    @PrimaryKey(autoGenerate = true) override var _id: Long = 0
}
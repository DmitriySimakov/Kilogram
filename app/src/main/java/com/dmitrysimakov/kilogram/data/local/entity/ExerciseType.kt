package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_type")
data class ExerciseType(@PrimaryKey val name: String)
package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseTarget(@PrimaryKey val name: String)
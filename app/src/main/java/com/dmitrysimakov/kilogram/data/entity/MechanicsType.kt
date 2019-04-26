package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mechanics_type")
data class MechanicsType(
        @PrimaryKey(autoGenerate = true) val _id: Long = 0,
        val name: String
)
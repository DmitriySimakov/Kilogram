package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipment")
data class Equipment(
        @PrimaryKey(autoGenerate = true) val _id: Long = 0,
        val name: String
)
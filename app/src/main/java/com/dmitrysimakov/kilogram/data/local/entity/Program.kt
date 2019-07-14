package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "program")
data class Program(
        @PrimaryKey(autoGenerate = true) val _id: Long = 0,
        val name: String,
        val description: String = ""
)
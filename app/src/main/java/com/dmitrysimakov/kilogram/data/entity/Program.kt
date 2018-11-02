package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.util.HasId

@Entity(tableName = "program")
data class Program(
        @PrimaryKey(autoGenerate = true) override val _id: Long = 0,
        val name: String
) : HasId
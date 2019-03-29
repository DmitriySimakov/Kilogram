package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.util.HasId

@Entity(tableName = "mechanics_type")
data class MechanicsType(
        @PrimaryKey(autoGenerate = true) override val _id: Long = 0,
        val name: String
) : HasId
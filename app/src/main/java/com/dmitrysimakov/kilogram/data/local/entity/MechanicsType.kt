package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mechanics_type")
data class MechanicsType(@PrimaryKey val name: String)
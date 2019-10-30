package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscle")
data class Muscle(@PrimaryKey val name: String)
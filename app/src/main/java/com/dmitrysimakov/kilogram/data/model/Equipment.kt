package com.dmitrysimakov.kilogram.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Equipment(@PrimaryKey val name: String)
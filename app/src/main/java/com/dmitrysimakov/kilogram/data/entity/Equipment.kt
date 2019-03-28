package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.util.HasId
import com.dmitrysimakov.kilogram.util.HasName

@Entity(tableName = "equipment")
data class Equipment(
        @PrimaryKey(autoGenerate = true) override val _id: Long = 0,
        override val name: String
) : HasId, HasName
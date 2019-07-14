package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "program_day",
        indices = [Index(value = ["program_id"])],
        foreignKeys = [
            ForeignKey(
                    entity = Program::class,
                    parentColumns = ["_id"],
                    childColumns = ["program_id"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class ProgramDay(
        @PrimaryKey(autoGenerate = true) val _id: Long = 0,
        val program_id: Long,
        var num: Int,
        val name: String,
        val description: String = ""
)
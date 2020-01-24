package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["programId"])],
        foreignKeys = [
            ForeignKey(
                    entity = Program::class,
                    parentColumns = ["id"],
                    childColumns = ["programId"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class ProgramDay(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val programId: Long,
        var indexNumber: Int,
        val name: String,
        val description: String = ""
)
package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "training",
        indices = [Index(value = ["program_day_id"])],
        foreignKeys = [
            ForeignKey(
                    entity = ProgramDay::class,
                    parentColumns = ["_id"],
                    childColumns = ["program_day_id"],
                    onDelete = ForeignKey.SET_NULL)
        ]
)
data class Training(
        @PrimaryKey(autoGenerate = true) val _id: Long = 0,
        var program_day_id: Long? = null,
        val start_time: Long,
        var duration: Int? = null
)
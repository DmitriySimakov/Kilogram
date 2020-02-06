package com.dmitrysimakov.kilogram.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.util.generateId
import java.util.*

@Entity(indices = [Index(value = ["programDayId"])],
        foreignKeys = [
            ForeignKey(
                    entity = ProgramDay::class,
                    parentColumns = ["id"],
                    childColumns = ["programDayId"],
                    onDelete = ForeignKey.SET_NULL)
        ]
)
data class Training(
        val startDateTime: Date = Date(),
        var programDayId: String? = null,
        var duration: Int? = null,
        val lastUpdate: Date = Date(),
        val deleted: Boolean = false,
        @PrimaryKey val id: String = generateId()
)
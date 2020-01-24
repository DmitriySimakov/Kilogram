package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

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
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val startDateTime: OffsetDateTime,
        var duration: Int? = null,
        var programDayId: Long? = null
)
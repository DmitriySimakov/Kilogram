package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.util.HasId

@Entity(tableName = "training",
        foreignKeys = [
            ForeignKey(
                    entity = ProgramDay::class,
                    parentColumns = ["_id"],
                    childColumns = ["program_day_id"],
                    onDelete = ForeignKey.SET_NULL)
        ]
)
data class Training(
        val date_time: String,
        val program_day_id: Long? = null,
        val duration: Int? = null
) : HasId {
    @PrimaryKey(autoGenerate = true) override var _id: Long = 0
}
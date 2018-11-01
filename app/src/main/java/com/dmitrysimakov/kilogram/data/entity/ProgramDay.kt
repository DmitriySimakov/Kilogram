package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.util.HasId

@Entity(tableName = "program_day",
        foreignKeys = [
            ForeignKey(
                    entity = Program::class,
                    parentColumns = ["_id"],
                    childColumns = ["program_id"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class ProgramDay(
        val name: String,
        val number: Byte,
        val program_id: Long? = null
) : HasId {
    @PrimaryKey(autoGenerate = true) override var _id: Long = 0
}
package com.dmitrysimakov.kilogram.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.dmitrysimakov.kilogram.data.entity.Equipment

@Dao
interface EquipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Equipment>)
}
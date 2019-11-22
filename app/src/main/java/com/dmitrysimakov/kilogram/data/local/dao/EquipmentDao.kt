package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.Equipment
import com.dmitrysimakov.kilogram.data.local.relation.FilterParam

@Dao
interface EquipmentDao {
    
    @Query("SELECT name, 0 AS is_active FROM equipment")
    suspend fun params(): List<FilterParam>
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(equipments: List<Equipment>)
}
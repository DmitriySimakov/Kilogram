package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.model.Equipment
import com.dmitrysimakov.kilogram.data.relation.FilterParam

@Dao
interface EquipmentDao {
    
    @Query("SELECT name, 0 AS isActive FROM Equipment")
    suspend fun params(): List<FilterParam>
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(equipments: List<Equipment>)
}
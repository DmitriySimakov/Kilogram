package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.Equipment
import com.dmitrysimakov.kilogram.data.relation.FilterParam

@Dao
interface EquipmentDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<Equipment>)
    
    @Query("SELECT name, 0 AS is_active FROM equipment")
    fun getParamList(): LiveData<List<FilterParam>>
}
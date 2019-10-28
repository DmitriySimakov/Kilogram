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
    
    @Query("SELECT _id, name, 0 AS is_active FROM equipment ORDER BY _id")
    fun getParams(): LiveData<List<FilterParam>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Equipment>)
}
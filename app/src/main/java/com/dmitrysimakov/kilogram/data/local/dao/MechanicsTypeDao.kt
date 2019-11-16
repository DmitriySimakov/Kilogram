package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.MechanicsType
import com.dmitrysimakov.kilogram.data.relation.FilterParam

@Dao
interface MechanicsTypeDao {
    
    @Query("SELECT name, 0 AS is_active FROM mechanics_type")
    suspend fun params(): List<FilterParam>
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mechanicsTypes: List<MechanicsType>)
}
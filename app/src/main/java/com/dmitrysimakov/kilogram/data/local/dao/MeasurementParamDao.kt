package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.model.MeasurementParam

@Dao
interface MeasurementParamDao {

    @Query("SELECT * FROM MeasurementParam")
    suspend fun params() : List<MeasurementParam>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(measurementParams: List<MeasurementParam>)
}
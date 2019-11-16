package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.dmitrysimakov.kilogram.data.local.entity.MeasurementParam

@Dao
interface MeasurementParamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(measurementParams: List<MeasurementParam>)
}
package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.Measurement
import com.dmitrysimakov.kilogram.data.relation.MeasurementWithPreviousResults

@Dao
interface MeasurementDao {
    
    @Query("""
        SELECT _id, param AS param,
        (SELECT value FROM measurement
            WHERE param = bmp.param
            ORDER BY date DESC LIMIT 0, 1) AS value,
        (SELECT value FROM measurement
            WHERE param = bmp.param
            ORDER BY date DESC LIMIT 1, 1) AS prevValue,
        (SELECT date FROM measurement
            WHERE param = bmp.param
            ORDER BY date DESC LIMIT 0, 1) AS date,
        (SELECT date FROM measurement
            WHERE param = bmp.param
            ORDER BY date DESC LIMIT 1, 1) AS prevDate
        FROM measurement AS bmp
    """)
    suspend fun measurementsWithPreviousResults() : List<MeasurementWithPreviousResults>

    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(measurements: List<Measurement>)
}
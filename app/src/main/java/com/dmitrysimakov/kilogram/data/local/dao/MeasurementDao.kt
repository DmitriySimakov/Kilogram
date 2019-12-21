package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.Measurement
import com.dmitrysimakov.kilogram.data.local.relation.MeasurementWithPreviousResults
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

@Dao
interface MeasurementDao {
    
    @Query("""
        SELECT
        (SELECT _id FROM measurement WHERE param = mp.name ORDER BY date DESC LIMIT 0, 1) AS _id,
        mp.name AS param,
        (SELECT value FROM measurement WHERE param = mp.name ORDER BY date DESC LIMIT 0, 1) AS value,
        (SELECT date FROM measurement WHERE param = mp.name ORDER BY date DESC LIMIT 0, 1) AS date,
        (SELECT value FROM measurement WHERE param = mp.name ORDER BY date DESC LIMIT 1, 1) AS prev_value,
        (SELECT date FROM measurement WHERE param = mp.name ORDER BY date DESC LIMIT 1, 1) AS prev_date
        FROM measurement_param AS mp
    """)
    fun lastMeasurementsWithPreviousResults() : Flow<List<MeasurementWithPreviousResults>>
    
    @Query("""
        SELECT m._id, m.param, m.value, m.date,
        (SELECT value FROM measurement WHERE param = m.param ORDER BY date DESC LIMIT 1, 1) AS prev_value,
        (SELECT date FROM measurement WHERE param = m.param ORDER BY date DESC LIMIT 1, 1) AS prev_date
        FROM measurement AS m
        WHERE m.date = :date
    """)
    fun measurementsWithPreviousResults(date: LocalDate) : Flow<List<MeasurementWithPreviousResults>>

    @Query("SELECT date FROM measurement GROUP BY date ORDER BY date DESC")
    fun measurementDates() : Flow<List<LocalDate>>
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(measurements: List<Measurement>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(measurement: Measurement)
}
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
        SELECT cur._id, cur.param, cur.date, cur.value, prev.date AS prev_date, prev.value AS prev_value
        FROM (SELECT m.param, MAX(m.date) AS date FROM measurement AS m GROUP BY m.param) AS x
        INNER JOIN measurement AS cur ON x.param = cur.param AND x.date = cur.date
        LEFT JOIN (SELECT * FROM measurement ORDER BY date LIMIT 1) AS prev
        ON x.param = prev.param AND x.date > prev.date
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
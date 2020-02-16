package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.model.Measurement
import com.dmitrysimakov.kilogram.data.relation.MeasurementWithPreviousResults
import com.dmitrysimakov.kilogram.data.relation.NewMeasurement
import com.dmitrysimakov.kilogram.data.relation.ProportionsCalculatorItem
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface MeasurementDao {
    
    @Query("SELECT * FROM Measurement WHERE id = :id")
    suspend fun measurement(id: String) : Measurement
    
    @Query("""
        SELECT MP.name AS param, MP.coefficient,
        (SELECT value FROM Measurement WHERE param = MP.name ORDER BY date DESC LIMIT 1) AS value
        FROM MeasurementParam AS MP
    """)
    suspend fun lastMeasurementsWithCoefficients() : List<ProportionsCalculatorItem>
    
    @Query("""
        SELECT
        (SELECT id FROM Measurement WHERE param = MP.name ORDER BY date DESC LIMIT 0, 1) AS id,
        MP.name AS param,
        (SELECT date FROM Measurement WHERE param = MP.name ORDER BY date DESC LIMIT 0, 1) AS date,
        (SELECT value FROM Measurement WHERE param = MP.name ORDER BY date DESC LIMIT 0, 1) AS value,
        (SELECT date FROM Measurement WHERE param = MP.name ORDER BY date DESC LIMIT 1, 1) AS prevDate,
        (SELECT value FROM Measurement WHERE param = MP.name ORDER BY date DESC LIMIT 1, 1) AS prevValue
        FROM MeasurementParam AS MP
    """)
    fun lastMeasurementsWithPreviousResults() : Flow<List<MeasurementWithPreviousResults>>
    
    @Query("""
        SELECT M.id, M.param, M.value, M.date,
        (SELECT value FROM Measurement WHERE param = M.param ORDER BY date DESC LIMIT 1, 1) AS prevValue,
        (SELECT date FROM Measurement WHERE param = M.param ORDER BY date DESC LIMIT 1, 1) AS prevDate
        FROM Measurement AS M
        WHERE M.date = :date
    """)
    fun measurementsWithPreviousResults(date: Date) : Flow<List<MeasurementWithPreviousResults>>

    @Query("""
        SELECT MP.name AS param, M.value, M.id
        FROM MeasurementParam AS MP
        LEFT JOIN Measurement AS M ON MP.name = M.param AND M.date = :date
    """)
    suspend fun measurements(date: Date) : List<NewMeasurement>
    
    @Query("SELECT date FROM Measurement GROUP BY date ORDER BY date DESC")
    fun measurementDates() : Flow<List<Date>>
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(measurements: List<Measurement>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(measurement: Measurement)
    
    
    @Query("DELETE FROM Measurement WHERE id = :id")
    suspend fun delete(id: String)
}
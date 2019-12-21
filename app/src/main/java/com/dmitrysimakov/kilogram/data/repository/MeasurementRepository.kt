package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.MeasurementDao
import com.dmitrysimakov.kilogram.data.local.entity.Measurement
import org.threeten.bp.LocalDate

class MeasurementRepository(private val dao: MeasurementDao) {
    
    fun lastMeasurementsWithPreviousResults() = dao.lastMeasurementsWithPreviousResults()
    
    fun measurementsWithPreviousResults(date: LocalDate) = dao.measurementsWithPreviousResults(date)
    
    fun measurementDates() = dao.measurementDates()
    
    suspend fun insert(measurement: Measurement) = dao.insert(measurement)
}

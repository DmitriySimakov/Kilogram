package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.MeasurementDao
import com.dmitrysimakov.kilogram.data.local.entity.Measurement
import com.dmitrysimakov.kilogram.data.remote.data_sources.MeasurementSource
import org.threeten.bp.LocalDate

class MeasurementRepository(
        private val dao: MeasurementDao,
        private val src: MeasurementSource
) {
    
    suspend fun measurement(id: Long) = dao.measurement(id)
    
    suspend fun lastMeasurementsWithCoefficients() = dao.lastMeasurementsWithCoefficients()
    
    fun lastMeasurementsWithPreviousResults() = dao.lastMeasurementsWithPreviousResults()
    
    fun measurementsWithPreviousResults(date: LocalDate) = dao.measurementsWithPreviousResults(date)
    
    fun measurementDates() = dao.measurementDates()
    
    suspend fun insert(measurement: Measurement) {
        val id = dao.insert(measurement)
        src.uploadMeasurement(id)
    }
}

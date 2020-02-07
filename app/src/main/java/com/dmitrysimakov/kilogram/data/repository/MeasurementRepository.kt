package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.MeasurementDao
import com.dmitrysimakov.kilogram.data.model.Measurement
import com.dmitrysimakov.kilogram.data.remote.data_sources.MeasurementSource
import java.util.*

class MeasurementRepository(
        private val dao: MeasurementDao,
        private val src: MeasurementSource
) {
    
    suspend fun lastMeasurementsWithCoefficients() = dao.lastMeasurementsWithCoefficients()
    
    fun lastMeasurementsWithPreviousResults() = dao.lastMeasurementsWithPreviousResults()
    
    fun measurementsWithPreviousResults(date: Date) = dao.measurementsWithPreviousResults(date)
    
    fun measurementDates() = dao.measurementDates()
    
    suspend fun insert(measurement: Measurement) {
        dao.insert(measurement)
        src.uploadMeasurement(measurement.id)
    }
}

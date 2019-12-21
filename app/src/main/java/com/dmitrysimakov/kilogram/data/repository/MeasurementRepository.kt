package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.MeasurementDao
import com.dmitrysimakov.kilogram.data.local.entity.Measurement

class MeasurementRepository(private val dao: MeasurementDao) {
    
    fun measurementsWithPreviousResults() = dao.measurementsWithPreviousResults()
    
    fun measurementDates() = dao.measurementDates()
    
    suspend fun insert(measurement: Measurement) = dao.insert(measurement)
}

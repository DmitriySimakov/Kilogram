package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.MeasurementDao
import com.dmitrysimakov.kilogram.data.model.Measurement
import com.dmitrysimakov.kilogram.data.remote.data_sources.MeasurementSource
import com.dmitrysimakov.kilogram.workers.UploadMeasurementWorker
import java.util.*

class MeasurementRepository(
        private val dao: MeasurementDao,
        private val src: MeasurementSource
) {
    
    suspend fun lastMeasurementsWithCoefficients() = dao.lastMeasurementsWithCoefficients()
    
    suspend fun measurement(id: String) = dao.measurement(id)
    
    suspend fun measurements(date: Date) = dao.measurements(date)
    
    fun lastMeasurementsWithPreviousResults() = dao.lastMeasurementsWithPreviousResults()
    
    fun measurementsWithPreviousResults(date: Date) = dao.measurementsWithPreviousResults(date)
    
    fun measurementDates() = dao.measurementDates()
    
    suspend fun insert(measurement: Measurement) {
        dao.insert(measurement)
        src.scheduleUpload(measurement.id, UploadMeasurementWorker::class.java)
    }
    
    suspend fun delete(id: String) {
        dao.delete(id)
        src.scheduleDeletion(id, UploadMeasurementWorker::class.java)
    }
    
    fun uploadMeasurement(measurement: Measurement) { src.uploadMeasurement(measurement) }
    
    suspend fun syncMeasurements(lastUpdate: Long) {
        val items = src.newMeasurements(lastUpdate)
        val (deletedItems, existingItems) = items.partition { it.deleted }
        
        deletedItems.forEach { dao.delete(it.id) }
        dao.insert(existingItems)
    }
}

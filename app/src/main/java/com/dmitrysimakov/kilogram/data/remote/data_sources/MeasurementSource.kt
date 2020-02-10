package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.data.model.Measurement
import com.dmitrysimakov.kilogram.data.remote.firestore
import com.dmitrysimakov.kilogram.data.remote.uid

class MeasurementSource(workManager: WorkManager) : RemoteDataSource(workManager) {
    
    private val measurementsRef
        get() = firestore.collection("users/$uid/measurements")
    
    suspend fun newMeasurements(lastUpdate: Long) =
            getNewData(Measurement::class.java, measurementsRef, lastUpdate)
    
    fun uploadMeasurement(measurement: Measurement) {
        measurementsRef.document(measurement.id).set(measurement)
    }
}
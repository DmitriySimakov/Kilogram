package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.data.model.Measurement
import com.dmitrysimakov.kilogram.data.remote.firestore
import com.dmitrysimakov.kilogram.data.remote.getNewData
import com.dmitrysimakov.kilogram.data.remote.uid
import com.dmitrysimakov.kilogram.workers.UploadMeasurementWorker

class MeasurementSource(workManager: WorkManager) : RemoteDataSource(workManager) {
    
    private val measurementsRef
        get() = firestore.collection("users/$uid/measurements")
    
    suspend fun newMeasurements(lastUpdate: Long) =
            getNewData(Measurement::class.java, measurementsRef, lastUpdate)
    
    fun uploadMeasurement(id: String) { upload(id, UploadMeasurementWorker::class.java) }
    fun deleteMeasurement(id: String) { delete(id, UploadMeasurementWorker::class.java) }
}
package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.workers.UploadMeasurementWorker

class MeasurementSource(workManager: WorkManager) : RemoteDataSource(workManager) {
    
    fun uploadMeasurement(id: String) { upload(id, UploadMeasurementWorker::class.java) }
    fun deleteMeasurement(id: String) { delete(id, UploadMeasurementWorker::class.java) }
}
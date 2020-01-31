package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.workers.UploadMeasurementWorker

class MeasurementSource(workManager: WorkManager) : RemoteDataSource(workManager) {
    
    fun uploadMeasurement(id: Long) { upload(id, UploadMeasurementWorker::class.java) }
    fun deleteMeasurement(id: Long) { delete(id, UploadMeasurementWorker::class.java) }
}
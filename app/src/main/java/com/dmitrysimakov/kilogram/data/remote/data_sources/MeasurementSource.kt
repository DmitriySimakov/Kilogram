package com.dmitrysimakov.kilogram.data.remote.data_sources

import android.content.Context
import com.dmitrysimakov.kilogram.workers.UploadMeasurementWorker

class MeasurementSource(context: Context) : RemoteDataSource(context) {
    
    fun uploadMeasurement(id: Long) { upload(id, UploadMeasurementWorker::class.java) }
}
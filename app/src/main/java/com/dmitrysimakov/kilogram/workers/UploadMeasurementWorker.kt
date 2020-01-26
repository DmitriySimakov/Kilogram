package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.MeasurementDao
import com.dmitrysimakov.kilogram.data.remote.models.Measurement
import com.dmitrysimakov.kilogram.util.measurementsCollection
import com.dmitrysimakov.kilogram.util.toDate
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadMeasurementWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: MeasurementDao by inject()
    
    override suspend fun doWork(): Result {
        val measurementId = inputData.getLong("id", 0)
        val (_, date, param, value) = dao.measurement(measurementId)
        
        val measurement = Measurement(measurementId, date.toDate(), param, value)
        measurementsCollection.document(measurementId.toString()).set(measurement)
        
        return Result.success()
    }
}
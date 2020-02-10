package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadMeasurementWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repo: MeasurementRepository by inject()
    
    override suspend fun doWork(): Result {
        return try {
            repo.uploadMeasurement(repo.measurement(inputData.getString(ID)!!))
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
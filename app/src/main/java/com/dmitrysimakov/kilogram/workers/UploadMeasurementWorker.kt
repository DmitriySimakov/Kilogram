package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class UploadMeasurementWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repo: MeasurementRepository by inject()
    
    override suspend fun doWork(): Result {
        return try {
            val id = inputData.getString(ID)!!
            val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
            repo.uploadMeasurement(repo.measurement(id).copy(deleted = needToDelete, lastUpdate = Date()))
        
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
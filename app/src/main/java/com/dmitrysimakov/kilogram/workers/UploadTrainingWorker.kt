package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repo: TrainingRepository by inject()
    
    override suspend fun doWork(): Result {
        return try {
            val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
            var training = repo.training(inputData.getString(ID)!!)
            if (needToDelete) training = training.copy(deleted = true)
        
            repo.uploadTraining(training)
        
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.repository.TrainingSetRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingSetWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repo: TrainingSetRepository by inject()
    
    override suspend fun doWork(): Result {
        return try {
            val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
            var trainingSet = repo.trainingSet(inputData.getString(ID)!!)
            if (needToDelete) trainingSet = trainingSet.copy(deleted = true)
        
            repo.uploadTrainingSet(trainingSet)
        
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
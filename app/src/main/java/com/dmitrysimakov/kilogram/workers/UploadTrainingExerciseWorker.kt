package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingExerciseWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repo: TrainingExerciseRepository by inject()
    
    override suspend fun doWork(): Result {
        return try {
            val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
            var trainingExercise = repo.trainingExercise(inputData.getString(ID)!!)
            if (needToDelete) trainingExercise = trainingExercise.copy(deleted = true)
        
            repo.uploadTrainingExercise(trainingExercise)
        
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
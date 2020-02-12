package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingExerciseListWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repo: TrainingExerciseRepository by inject()
    
    override suspend fun doWork(): Result {
        return try {
            val trainingId = inputData.getString(ID)!!
            repo.uploadTrainingExercises(repo.trainingExercises(trainingId))
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.trainingsCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams), KoinComponent {
    
    private val trainingRepository: TrainingRepository by inject()
    
    override suspend fun doWork(): Result {
        val trainingId = inputData.getLong("id", 0)
        val training = trainingRepository.training(trainingId)
        
        trainingsCollection.document(trainingId.toString()).set(training)
        
        return Result.success()
    }
}
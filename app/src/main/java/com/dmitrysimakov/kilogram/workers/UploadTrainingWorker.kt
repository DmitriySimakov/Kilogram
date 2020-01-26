package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.models.Training
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.toDate
import com.dmitrysimakov.kilogram.util.trainingsCollection
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class UploadTrainingWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repository: TrainingRepository by inject()
    
    override suspend fun doWork(): Result {
        val trainingId = inputData.getLong("id", 0)
        val (id, startDateTime, duration, programDayId) = repository.training(trainingId)
        
        val training = Training(id, startDateTime.toDate(), duration, programDayId, Date())
        trainingsCollection.document(id.toString()).set(training)
        
        return Result.success()
    }
}
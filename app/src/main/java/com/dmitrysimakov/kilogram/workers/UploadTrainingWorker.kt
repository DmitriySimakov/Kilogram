package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.remote.models.Training
import com.dmitrysimakov.kilogram.util.toDate
import com.dmitrysimakov.kilogram.util.trainingsCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: TrainingDao by inject()
    
    override suspend fun doWork(): Result {
        val trainingId = inputData.getLong("id", 0)
        val (_, startDateTime, duration, programDayId) = dao.training(trainingId)
        
        val training = Training(trainingId, startDateTime.toDate(), duration, programDayId)
        trainingsCollection.document(trainingId.toString()).set(training)
        
        return Result.success()
    }
}
package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.TrainingSetDao
import com.dmitrysimakov.kilogram.data.remote.models.TrainingSet
import com.dmitrysimakov.kilogram.util.trainingSetsCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingSetWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: TrainingSetDao by inject()
    
    override suspend fun doWork(): Result {
        val trainingSetId = inputData.getLong("id", 0)
        val (_, trainingExerciseId, weight, reps, time, distance) = dao.trainingSet(trainingSetId)
        
        val trainingSet = TrainingSet(trainingSetId, trainingExerciseId, weight, reps, time, distance)
        trainingSetsCollection.document(trainingSetId.toString()).set(trainingSet)
        
        return Result.success()
    }
}
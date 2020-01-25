package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.TrainingSet
import com.dmitrysimakov.kilogram.data.repository.TrainingSetRepository
import com.dmitrysimakov.kilogram.util.toDate
import com.dmitrysimakov.kilogram.util.trainingSetsCollection
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class UploadTrainingSetWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repository: TrainingSetRepository by inject()
    
    override suspend fun doWork(): Result {
        val trainingSetId = inputData.getLong("id", 0)
        val set = repository.trainingSet(trainingSetId)
        
        val trainingSet = TrainingSet(set.id, set.trainingExerciseId, set.weight, set.reps, set.time, set.distance, set.dateTime?.toDate(), Date())
        trainingSetsCollection.document(set.id.toString()).set(trainingSet)
        
        return Result.success()
    }
}
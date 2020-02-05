package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.TrainingSetDao
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.remote.models.TrainingSet
import com.dmitrysimakov.kilogram.util.userTrainingSetsCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingSetWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: TrainingSetDao by inject()
    
    override suspend fun doWork(): Result {
        val trainingSetId = inputData.getLong(ID, 0)
        val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
        val trainingSet = if (needToDelete) {
            TrainingSet(trainingSetId, deleted = true)
        } else {
            val (_, trainingExerciseId, weight, reps, time, distance) = dao.trainingSet(trainingSetId)
            TrainingSet(trainingSetId, trainingExerciseId, weight, reps, time, distance)
        }
        
        userTrainingSetsCollection.document(trainingSetId.toString()).set(trainingSet)
        
        return Result.success()
    }
}
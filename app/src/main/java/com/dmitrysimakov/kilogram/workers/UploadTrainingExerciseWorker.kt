package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.remote.models.TrainingExercise
import com.dmitrysimakov.kilogram.util.userTrainingExercisesCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingExerciseWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: TrainingExerciseDao by inject()
    
    override suspend fun doWork(): Result {
        val trainingExerciseId = inputData.getLong(ID, 0)
        val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
        val trainingExercise = if (needToDelete) {
            TrainingExercise(trainingExerciseId, deleted = true)
        } else {
            val (_, trainingId, exercise, indexNumber, rest, strategy, state) = dao.trainingExercise(trainingExerciseId)
            TrainingExercise(trainingExerciseId, trainingId, exercise, indexNumber, rest, strategy, state)
        }
        
        userTrainingExercisesCollection.document(trainingExerciseId.toString()).set(trainingExercise)
        
        return Result.success()
    }
}
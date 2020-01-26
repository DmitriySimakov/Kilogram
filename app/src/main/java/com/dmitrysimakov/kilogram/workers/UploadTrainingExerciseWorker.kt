package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.remote.models.TrainingExercise
import com.dmitrysimakov.kilogram.util.trainingExercisesCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingExerciseWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: TrainingExerciseDao by inject()
    
    override suspend fun doWork(): Result {
        val trainingExerciseId = inputData.getLong("id", 0)
        val (_, trainingId, exercise, indexNumber, rest, strategy, state) = dao.trainingExercise(trainingExerciseId)
        
        val trainingExercise = TrainingExercise(trainingExerciseId, trainingId, exercise, indexNumber, rest, strategy, state)
        trainingExercisesCollection.document(trainingExerciseId.toString()).set(trainingExercise)
        
        return Result.success()
    }
}
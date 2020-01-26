package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.models.TrainingExercise
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.util.trainingExercisesCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingExerciseWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repository: TrainingExerciseRepository by inject()
    
    override suspend fun doWork(): Result {
        val trainingExerciseId = inputData.getLong("id", 0)
        val (id, trainingId, exercise, indexNumber, rest, strategy, state) = repository.trainingExercise(trainingExerciseId)
        
        val trainingExercise = TrainingExercise(id, trainingId, exercise, indexNumber, rest, strategy, state)
        trainingExercisesCollection.document(id.toString()).set(trainingExercise)
        
        return Result.success()
    }
}
package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.models.TrainingExercise
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.util.firestore
import com.dmitrysimakov.kilogram.util.trainingExercisesCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingExerciseListWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repository: TrainingExerciseRepository by inject()
    
    override suspend fun doWork(): Result {
        val trainingId = inputData.getLong("id", 0)
        val exercises = repository.trainingExercises(trainingId)
        
        val writeBatch = firestore.batch()
        for (e in exercises) {
            val remoteExercise = TrainingExercise(e.id, e.trainingId, e.exercise, e.indexNumber, e.rest, e.strategy, e.state)
            writeBatch.set(trainingExercisesCollection.document(remoteExercise.id.toString()), remoteExercise)
        }
        writeBatch.commit()
        
        return Result.success()
    }
}
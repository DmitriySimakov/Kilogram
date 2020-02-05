package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.models.TrainingExercise
import com.dmitrysimakov.kilogram.util.firestore
import com.dmitrysimakov.kilogram.util.userTrainingExercisesCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingExerciseListWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: TrainingExerciseDao by inject()
    
    override suspend fun doWork(): Result {
        val trainingId = inputData.getLong(ID, 0)
        val exercises = dao.trainingExercises(trainingId)
        
        val writeBatch = firestore.batch()
        for (e in exercises) {
            val remoteExercise = TrainingExercise(e.id, e.trainingId, e.exercise, e.indexNumber, e.rest, e.strategy, e.state)
            writeBatch.set(userTrainingExercisesCollection.document(remoteExercise.id.toString()), remoteExercise)
        }
        writeBatch.commit()
        
        return Result.success()
    }
}
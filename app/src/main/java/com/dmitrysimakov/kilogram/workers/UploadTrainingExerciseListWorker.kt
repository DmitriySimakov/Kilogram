package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.util.firestore
import com.dmitrysimakov.kilogram.util.userTrainingExercisesCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingExerciseListWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: TrainingExerciseDao by inject()
    
    override suspend fun doWork(): Result {
        val trainingId = inputData.getString(ID)!!
        val exercises = dao.trainingExercises(trainingId)
        
        val writeBatch = firestore.batch()
        for (exercise in exercises) {
            writeBatch.set(userTrainingExercisesCollection.document(exercise.id), exercise)
        }
        writeBatch.commit()
        
        return Result.success()
    }
}
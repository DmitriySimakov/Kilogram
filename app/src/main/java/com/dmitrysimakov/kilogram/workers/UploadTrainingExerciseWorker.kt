package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.model.TrainingExercise
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.remote.userTrainingExercisesCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingExerciseWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: TrainingExerciseDao by inject()
    
    override suspend fun doWork(): Result {
        val trainingExerciseId = inputData.getString(ID)!!
        val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
        val trainingExercise = if (needToDelete) TrainingExercise(id = trainingExerciseId, deleted = true)
        else dao.trainingExercise(trainingExerciseId)
        
        userTrainingExercisesCollection.document(trainingExerciseId).set(trainingExercise)
        
        return Result.success()
    }
}
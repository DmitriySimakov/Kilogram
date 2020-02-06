package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.model.Training
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.util.userTrainingsCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: TrainingDao by inject()
    
    override suspend fun doWork(): Result {
        val trainingId = inputData.getString(ID)!!
        val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
        val training = if (needToDelete) Training(id = trainingId, deleted = true)
        else dao.training(trainingId)
        
        userTrainingsCollection.document(trainingId).set(training)
        
        return Result.success()
    }
}
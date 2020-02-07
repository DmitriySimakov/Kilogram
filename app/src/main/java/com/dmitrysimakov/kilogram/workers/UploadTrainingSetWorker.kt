package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.TrainingSetDao
import com.dmitrysimakov.kilogram.data.model.TrainingSet
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.remote.userTrainingSetsCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingSetWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: TrainingSetDao by inject()
    
    override suspend fun doWork(): Result {
        val trainingSetId = inputData.getString(ID)!!
        val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
        val trainingSet = if (needToDelete) TrainingSet(id = trainingSetId, deleted = true)
        else dao.trainingSet(trainingSetId)
        
        userTrainingSetsCollection.document(trainingSetId).set(trainingSet)
        
        return Result.success()
    }
}
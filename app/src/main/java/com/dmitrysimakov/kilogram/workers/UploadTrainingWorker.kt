package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.remote.models.Training
import com.dmitrysimakov.kilogram.util.toIsoString
import com.dmitrysimakov.kilogram.util.trainingsCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadTrainingWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: TrainingDao by inject()
    
    override suspend fun doWork(): Result {
        val trainingId = inputData.getLong(ID, 0)
        val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
        val training = if (needToDelete) {
            Training(trainingId, deleted = true)
        } else {
            val (_, startDateTime, duration, programDayId) = dao.training(trainingId)
            Training(trainingId, startDateTime.toIsoString(), duration, programDayId)
        }
        trainingsCollection.document(trainingId.toString()).set(training)
        
        return Result.success()
    }
}
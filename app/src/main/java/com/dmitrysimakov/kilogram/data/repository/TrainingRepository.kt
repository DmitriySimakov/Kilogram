package com.dmitrysimakov.kilogram.data.repository

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.remote.data_sources.TrainingSource
import com.dmitrysimakov.kilogram.workers.SyncTrainingsWorker
import org.threeten.bp.LocalDate
import java.util.concurrent.TimeUnit

private const val TRAINING_SYNC = "training sync"

class TrainingRepository(
        context: Context,
        private val dao: TrainingDao,
        private val src: TrainingSource
) {
    
    private val workManager = WorkManager.getInstance(context)
    
    fun detailedTrainingsFlow() = dao.detailedTrainingsFlow()
    
    suspend fun detailedTrainingsForDay(date: LocalDate) = dao.detailedTrainingsForDay(date)
    
    suspend fun training(id: Long) = dao.training(id)
    
    suspend fun insert(training: Training): Long {
        val id = dao.insert(training)
        src.uploadTraining(id)
        
        return id
    }
    
    suspend fun update(training: Training) {
        dao.update(training)
        src.uploadTraining(training.id)
    }
    
    suspend fun delete(id: Long) = dao.delete(id)
    
    fun runPeriodicTrainingSync() {
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()
        
        val request = PeriodicWorkRequest
                .Builder(SyncTrainingsWorker::class.java, 1, TimeUnit.DAYS)
                .addTag(TRAINING_SYNC)
                .setConstraints(constraints)
                .build()
        
        workManager.enqueue(request)
    }
    
    fun cancelTrainingSync() {
        workManager.cancelAllWorkByTag(TRAINING_SYNC)
    }
}
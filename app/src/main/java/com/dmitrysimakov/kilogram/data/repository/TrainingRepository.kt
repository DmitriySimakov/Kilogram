package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.local.entity.Training
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrainingRepository(
        private val trainingDao: TrainingDao,
        private val io: CoroutineDispatcher = Dispatchers.IO
) {
    
    suspend fun loadDetailedTrainingList() = withContext(io) {
        trainingDao.getDetailedTrainingList()
    }
    
    suspend fun loadTraining(id: Long) = withContext(io) {
        trainingDao.getTraining(id)
    }
    
    suspend fun insertTraining(training: Training) = withContext(io) {
        trainingDao.insert(training)
    }
    
    suspend fun updateTraining(training: Training) = withContext(io) {
        trainingDao.update(training)
    }
    
    suspend fun deleteTraining(id: Long) = withContext(io) {
        trainingDao.delete(id)
    }
}
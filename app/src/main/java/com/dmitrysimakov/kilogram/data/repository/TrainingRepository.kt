package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.local.entity.Training

class TrainingRepository(private val trainingDao: TrainingDao) {
    
    suspend fun loadDetailedTrainingList() =
            trainingDao.getDetailedTrainingList()
    
    suspend fun loadTraining(id: Long) =
            trainingDao.getTraining(id)
    
    suspend fun insertTraining(training: Training) {
        trainingDao.insert(training)
    }
    
    suspend fun updateTraining(training: Training) {
        trainingDao.update(training)
    }
    
    suspend fun deleteTraining(id: Long) {
        trainingDao.delete(id)
    }
}
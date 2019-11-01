package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.local.entity.Training

class TrainingRepository(private val trainingDao: TrainingDao) {
    
    fun loadDetailedTrainingList() = trainingDao.getDetailedTrainingList()
    
    fun loadTraining(id: Long) = trainingDao.getTraining(id)
    
    suspend fun insertTraining(training: Training, callback: ((Long) -> Unit)? = null) {
        val id = trainingDao.insert(training)
        callback?.invoke(id)
    }
    
    suspend fun updateTraining(training: Training) {
        trainingDao.update(training)
    }
    
    suspend fun deleteTraining(id: Long) {
        trainingDao.delete(id)
    }
}
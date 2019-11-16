package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.local.entity.Training

class TrainingRepository(private val trainingDao: TrainingDao) {
    
    fun detailedTrainingsFlow() =
            trainingDao.detailedTrainingsFlow()
    
    suspend fun training(id: Long) =
            trainingDao.training(id)
    
    suspend fun insert(training: Training) =
            trainingDao.insert(training)
    
    suspend fun update(training: Training) =
            trainingDao.update(training)
    
    suspend fun delete(id: Long) =
            trainingDao.delete(id)
}
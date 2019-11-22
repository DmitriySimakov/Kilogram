package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.local.entity.Training

class TrainingRepository(private val dao: TrainingDao) {
    
    fun detailedTrainingsFlow() = dao.detailedTrainingsFlow()
    
    suspend fun training(id: Long) = dao.training(id)
    
    suspend fun insert(training: Training) = dao.insert(training)
    
    suspend fun update(training: Training) = dao.update(training)
    
    suspend fun delete(id: Long) = dao.delete(id)
}
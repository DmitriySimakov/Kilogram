package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.model.Training
import com.dmitrysimakov.kilogram.data.remote.data_sources.TrainingSource
import java.util.*

class TrainingRepository(
        private val dao: TrainingDao,
        private val src: TrainingSource
) {
    
    fun detailedTrainingsFlow() = dao.detailedTrainingsFlow()
    
    suspend fun detailedTrainingsForDay(date: Date) = dao.detailedTrainingsForDay(date)
    
    suspend fun training(id: String) = dao.training(id)
    
    suspend fun insert(training: Training) {
        dao.insert(training)
        src.uploadTraining(training.id)
    }
    
    suspend fun update(training: Training) {
        dao.update(training)
        src.uploadTraining(training.id)
    }
    
    suspend fun delete(id: String) {
        dao.delete(id)
        src.deleteTraining(id)
    }
    
    suspend fun syncTrainings(lastUpdate: Long) {
        val items = src.newTrainings(lastUpdate)
        val (deletedItems, existingItems) = items.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        dao.insert(existingItems)
    }
}
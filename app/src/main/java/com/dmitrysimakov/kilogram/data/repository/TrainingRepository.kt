package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.remote.data_sources.TrainingSource
import org.threeten.bp.LocalDate

class TrainingRepository(
        private val dao: TrainingDao,
        private val src: TrainingSource
) {
    
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
}
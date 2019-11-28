package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.local.relation.DetailedTraining
import org.threeten.bp.LocalDate

class TrainingRepository(private val dao: TrainingDao) {
    
    fun detailedTrainingsFlow() = dao.detailedTrainingsFlow()
    
    suspend fun detailedTrainingsForDay(date: LocalDate) : List<DetailedTraining> {
        return dao.detailedTrainingsForDay()
    }
    
    suspend fun training(id: Long) = dao.training(id)
    
    suspend fun insert(training: Training) = dao.insert(training)
    
    suspend fun update(training: Training) = dao.update(training)
    
    suspend fun delete(id: Long) = dao.delete(id)
}
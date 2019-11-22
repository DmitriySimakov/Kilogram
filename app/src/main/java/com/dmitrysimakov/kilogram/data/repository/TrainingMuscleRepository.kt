package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingMuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingTarget

class TrainingMuscleRepository(private val dao: TrainingMuscleDao) {
    
    suspend fun params(trainingId: Long) = dao.params(trainingId)
    
    suspend fun insert(trainingTargets: List<TrainingTarget>) = dao.insert(trainingTargets)
}
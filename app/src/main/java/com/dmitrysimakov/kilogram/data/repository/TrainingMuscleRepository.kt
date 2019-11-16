package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingMuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingMuscle

class TrainingMuscleRepository(private val trainingMuscleDao: TrainingMuscleDao) {
    
    suspend fun params(trainingId: Long) =
            trainingMuscleDao.params(trainingId)
    
    suspend fun insert(trainingMuscles: List<TrainingMuscle>) =
            trainingMuscleDao.insert(trainingMuscles)
}
package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingMuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingMuscle

class TrainingMuscleRepository(private val trainingMuscleDao: TrainingMuscleDao) {
    
    suspend fun loadParams(trainingId: Long) = trainingMuscleDao.getParamList(trainingId)
    
    suspend fun insert(list: List<TrainingMuscle>) {
        trainingMuscleDao.insert(list)
    }
}
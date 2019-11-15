package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingMuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingMuscle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrainingMuscleRepository(
        private val trainingMuscleDao: TrainingMuscleDao,
        private val io: CoroutineDispatcher = Dispatchers.IO
) {
    
    suspend fun loadParams(trainingId: Long)  = withContext(io) {
        trainingMuscleDao.getParamList(trainingId)
    }
    
    suspend fun insert(list: List<TrainingMuscle>) = withContext(io) {
        trainingMuscleDao.insert(list)
    }
}
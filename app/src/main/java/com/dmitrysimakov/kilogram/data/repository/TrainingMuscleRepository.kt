package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingMuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingMuscle
import com.dmitrysimakov.kilogram.util.AppExecutors

class TrainingMuscleRepository(
        private val executors: AppExecutors,
        private val trainingMuscleDao: TrainingMuscleDao
) {
    
    fun loadParams(trainingId: Long) = trainingMuscleDao.getParams(trainingId)
    
    fun insert(list: List<TrainingMuscle>) {
        executors.diskIO().execute {
            trainingMuscleDao.insert(list)
        }
    }
}
package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.dao.TrainingMuscleDao
import com.dmitrysimakov.kilogram.data.entity.TrainingMuscle
import com.dmitrysimakov.kilogram.util.AppExecutors
import javax.inject.Inject

class TrainingMuscleRepository @Inject constructor(
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
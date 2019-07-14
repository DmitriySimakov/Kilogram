package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingRepository @Inject constructor(
        private val executors: AppExecutors,
        private val trainingDao: TrainingDao
) {

    fun loadTrainingList() = trainingDao.getTrainingList()
    
    fun loadTrainingRList() = trainingDao.getTrainingRList()
    
    fun loadTraining(id: Long) = trainingDao.getTraining(id)

    fun insertTraining(training: Training, callback: ((Long) -> Unit)? = null) {
        executors.diskIO().execute{
            val id = trainingDao.insert(training)
            executors.mainThread().execute {
                callback?.invoke(id)
            }
        }
    }

    fun updateTraining(training: Training) {
        executors.diskIO().execute{
            trainingDao.update(training)
        }
    }
    
    fun deleteTraining(id: Long) {
        executors.diskIO().execute{
            trainingDao.delete(id)
        }
    }
}
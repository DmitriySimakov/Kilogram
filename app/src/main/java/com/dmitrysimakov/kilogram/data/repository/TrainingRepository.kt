package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.data.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.entity.Training
import com.dmitrysimakov.kilogram.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingRepository @Inject constructor(
        private val executors: AppExecutors,
        private val trainingDao: TrainingDao
) {

    fun insertTraining(training: Training, callback: ItemInsertedListener? = null) {
        executors.diskIO().execute{
            val id = trainingDao.insert(training)
            executors.mainThread().execute {
                callback?.onItemInserted(id)
            }
        }
    }

    fun loadTrainingList() = trainingDao.getTrainingList()
}
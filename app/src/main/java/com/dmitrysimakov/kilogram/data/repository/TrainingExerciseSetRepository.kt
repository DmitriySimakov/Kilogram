package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseSetDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExerciseSet
import com.dmitrysimakov.kilogram.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingExerciseSetRepository @Inject constructor(
        private val executors: AppExecutors,
        private val trainingExerciseSetDao: TrainingExerciseSetDao
) {

    fun loadSets(training_exercise_id: Long) = trainingExerciseSetDao.getSets(training_exercise_id)
    
    fun loadSet(id: Long) = trainingExerciseSetDao.getSet(id)
    
    fun insertSet(set: TrainingExerciseSet) {
        executors.diskIO().execute{
            trainingExerciseSetDao.insert(set)
        }
    }

    fun deleteSet(set: TrainingExerciseSet) {
        executors.diskIO().execute{
            trainingExerciseSetDao.delete(set)
        }
    }
    
    fun deleteSet(id: Long) {
        executors.diskIO().execute{
            trainingExerciseSetDao.delete(id)
        }
    }
    
    fun updateSet(set: TrainingExerciseSet) {
        executors.diskIO().execute{
            trainingExerciseSetDao.update(set)
        }
    }
}
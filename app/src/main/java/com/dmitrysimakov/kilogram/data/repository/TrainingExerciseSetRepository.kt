package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.dao.TrainingExerciseSetDao
import com.dmitrysimakov.kilogram.data.entity.TrainingExerciseSet
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
    
    fun updateSet(set: TrainingExerciseSet) {
        executors.diskIO().execute{
            trainingExerciseSetDao.update(set)
        }
    }
}
package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseSetDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExerciseSet

class TrainingExerciseSetRepository(private val trainingExerciseSetDao: TrainingExerciseSetDao) {

    fun loadSets(training_exercise_id: Long) = trainingExerciseSetDao.getSets(training_exercise_id)
    
    fun loadSet(id: Long) = trainingExerciseSetDao.getSet(id)
    
    suspend fun insertSet(set: TrainingExerciseSet) {
        trainingExerciseSetDao.insert(set)
    }
    
    suspend fun deleteSet(set: TrainingExerciseSet) {
        trainingExerciseSetDao.delete(set)
    }
    
    suspend fun deleteSet(id: Long) {
        trainingExerciseSetDao.delete(id)
    }
    
    suspend fun updateSet(set: TrainingExerciseSet) {
        trainingExerciseSetDao.update(set)
    }
}
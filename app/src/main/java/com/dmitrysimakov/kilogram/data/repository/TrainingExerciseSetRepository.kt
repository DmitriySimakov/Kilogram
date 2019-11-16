package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseSetDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingSet

class TrainingExerciseSetRepository(private val trainingExerciseSetDao: TrainingExerciseSetDao) {

    suspend fun trainingSets(training_exercise_id: Long) =
            trainingExerciseSetDao.trainingSets(training_exercise_id)
    
    suspend fun trainingSet(id: Long) =
            trainingExerciseSetDao.trainingSet(id)
    
    suspend fun insert(set: TrainingSet) =
            trainingExerciseSetDao.insert(set)
    
    suspend fun update(set: TrainingSet) =
            trainingExerciseSetDao.update(set)
    
    suspend fun delete(id: Long) =
            trainingExerciseSetDao.delete(id)
}
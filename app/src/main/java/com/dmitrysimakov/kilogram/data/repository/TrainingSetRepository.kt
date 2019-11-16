package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseSetDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingSet

class TrainingSetRepository(private val trainingExerciseSetDao: TrainingExerciseSetDao) {

    fun trainingSetsFlow(training_exercise_id: Long) =
            trainingExerciseSetDao.trainingSetsFlow(training_exercise_id)
    
    suspend fun trainingSet(id: Long) =
            trainingExerciseSetDao.trainingSet(id)
    
    suspend fun insert(set: TrainingSet) =
            trainingExerciseSetDao.insert(set)
    
    suspend fun update(set: TrainingSet) =
            trainingExerciseSetDao.update(set)
    
    suspend fun delete(id: Long) =
            trainingExerciseSetDao.delete(id)
}
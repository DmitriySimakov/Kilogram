package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingSetDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingSet

class TrainingSetRepository(private val dao: TrainingSetDao) {

    fun trainingSetsFlow(training_exercise_id: Long) = dao.trainingSetsFlow(training_exercise_id)
    
    suspend fun trainingSet(id: Long) = dao.trainingSet(id)
    
    suspend fun insert(set: TrainingSet) = dao.insert(set)
    
    suspend fun update(set: TrainingSet) = dao.update(set)
    
    suspend fun delete(id: Long) = dao.delete(id)
}
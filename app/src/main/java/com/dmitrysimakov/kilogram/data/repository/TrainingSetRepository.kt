package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingSetDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingSet
import com.dmitrysimakov.kilogram.data.remote.data_sources.TrainingSource

class TrainingSetRepository(private val dao: TrainingSetDao, private val src: TrainingSource) {

    fun trainingSetsFlow(training_exercise_id: Long) = dao.trainingSetsFlow(training_exercise_id)
    
    suspend fun trainingSet(id: Long) = dao.trainingSet(id)
    
    suspend fun insert(set: TrainingSet) {
        val id = dao.insert(set)
        src.uploadTrainingSet(id)
    }
    
    suspend fun update(set: TrainingSet) {
        dao.update(set)
        src.uploadTrainingSet(set.id)
    }
    
    suspend fun delete(id: Long) {
        dao.delete(id)
        src.deleteTrainingSet(id)
    }
}
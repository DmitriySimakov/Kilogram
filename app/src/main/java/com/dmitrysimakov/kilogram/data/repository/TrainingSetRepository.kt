package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingSetDao
import com.dmitrysimakov.kilogram.data.model.TrainingSet
import com.dmitrysimakov.kilogram.data.remote.data_sources.TrainingSource

class TrainingSetRepository(private val dao: TrainingSetDao, private val src: TrainingSource) {

    fun trainingSetsFlow(trainingExerciseId: String) = dao.trainingSetsFlow(trainingExerciseId)
    
    suspend fun insert(set: TrainingSet) {
        dao.insert(set)
        src.uploadTrainingSet(set.id)
    }
    
    suspend fun update(set: TrainingSet) {
        dao.update(set)
        src.uploadTrainingSet(set.id)
    }
    
    suspend fun delete(id: String) {
        dao.delete(id)
        src.deleteTrainingSet(id)
    }
}
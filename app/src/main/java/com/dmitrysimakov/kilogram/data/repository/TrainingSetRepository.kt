package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingSetDao
import com.dmitrysimakov.kilogram.data.model.TrainingSet
import com.dmitrysimakov.kilogram.data.remote.data_sources.TrainingSource
import com.dmitrysimakov.kilogram.workers.UploadTrainingSetWorker

class TrainingSetRepository(private val dao: TrainingSetDao, private val src: TrainingSource) {

    fun trainingSetsFlow(trainingExerciseId: String) = dao.trainingSetsFlow(trainingExerciseId)
    
    suspend fun trainingSet(id: String) = dao.trainingSet(id)
    
    suspend fun insert(set: TrainingSet) {
        dao.insert(set)
        src.scheduleUpload(set.id, UploadTrainingSetWorker::class.java)
    }
    
    suspend fun update(set: TrainingSet) {
        dao.update(set)
        src.scheduleUpload(set.id, UploadTrainingSetWorker::class.java)
    }
    
    suspend fun delete(id: String) {
        dao.delete(id)
        src.scheduleDeletion(id, UploadTrainingSetWorker::class.java)
    }
    
    fun uploadTrainingSet(trainingSet: TrainingSet) { src.uploadTrainingSet(trainingSet) }
    
    suspend fun syncTrainingSets(lastUpdate: Long) {
        val items = src.newTrainingSets(lastUpdate)
        val (deletedItems, existingItems) = items.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        dao.insert(existingItems)
    }
}
package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.model.TrainingExercise
import com.dmitrysimakov.kilogram.data.remote.data_sources.TrainingSource
import com.dmitrysimakov.kilogram.workers.UploadTrainingExerciseListWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingExerciseWorker

class TrainingExerciseRepository(
        private val dao: TrainingExerciseDao,
        private val src: TrainingSource
) {
    
    fun trainingExercisesFlow(trainingId: String) = dao.trainingExercisesFlow(trainingId)
    
    suspend fun trainingExercises(trainingId: String) = dao.trainingExercises(trainingId)
    
    suspend fun previousTrainingExercise(trainingId: String, exercise: String) = dao.previousTrainingExercise(trainingId, exercise)
    
    fun trainingExerciseFlow(id: String) = dao.trainingExerciseFlow(id)
    
    suspend fun trainingExercise(id: String) = dao.trainingExercise(id)
    
    suspend fun insert(exercise: TrainingExercise) {
        dao.insert(exercise)
        src.scheduleUpload(exercise.id, UploadTrainingExerciseWorker::class.java)
    }
    
    suspend fun insert(trainingExercises: List<TrainingExercise>) {
        if (trainingExercises.isEmpty()) return
        
        dao.insert(trainingExercises)
        src.scheduleUpload(trainingExercises[0].trainingId, UploadTrainingExerciseListWorker::class.java)
    }
    
    suspend fun updateState(id: String, state: Int) {
        val exercise = dao.trainingExercise(id)
        val updatedExercise = exercise.copy(state = state)
        dao.update(updatedExercise)
        src.scheduleUpload(exercise.id, UploadTrainingExerciseWorker::class.java)
    }
    
    suspend fun updateIndexNumbers(trainingExercises: List<TrainingExercise>) = dao.updateIndexNumbers(trainingExercises)
    
    suspend fun update(trainingExercises: List<TrainingExercise>) {
        if (trainingExercises.isEmpty()) return
        
        dao.update(trainingExercises)
        src.scheduleUpload(trainingExercises[0].trainingId, UploadTrainingExerciseListWorker::class.java)
    }
    
    suspend fun delete(id: String) {
        dao.delete(id)
        src.scheduleDeletion(id, UploadTrainingExerciseWorker::class.java)
    }
    
    fun uploadTrainingExercises(trainingExercises: List<TrainingExercise>) {
        trainingExercises.forEach { src.uploadTrainingExercise(it) }
    }
    
    fun uploadTrainingExercise(trainingExercise: TrainingExercise) {
        src.uploadTrainingExercise(trainingExercise)
    }
    
    suspend fun syncTrainingExercises(lastUpdate: Long) {
        val items = src.newTrainingExercises(lastUpdate)
        val (deletedItems, existingItems) = items.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        dao.insert(existingItems)
    }
}
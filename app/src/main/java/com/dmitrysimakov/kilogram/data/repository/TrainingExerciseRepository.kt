package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.model.TrainingExercise
import com.dmitrysimakov.kilogram.data.remote.data_sources.TrainingSource

class TrainingExerciseRepository(
        private val dao: TrainingExerciseDao,
        private val src: TrainingSource
) {
    
    fun trainingExercisesFlow(trainingId: String) = dao.trainingExercisesFlow(trainingId)
    
    suspend fun previousTrainingExercise(trainingId: String, exercise: String) = dao.previousTrainingExercise(trainingId, exercise)
    
    fun trainingExerciseFlow(id: String) = dao.trainingExerciseFlow(id)
    
    suspend fun trainingExercise(id: String) = dao.trainingExercise(id)
    
    suspend fun insert(exercise: TrainingExercise) {
        dao.insert(exercise)
        src.uploadTrainingExercise(exercise.id)
    }
    
    suspend fun insert(exercises: List<TrainingExercise>) {
        if (exercises.isEmpty()) return
        
        dao.insert(exercises)
        src.uploadTrainingExerciseList(exercises[0].trainingId)
    }
    
    suspend fun updateState(id: String, state: Int) {
        val exercise = dao.trainingExercise(id)
        val updatedExercise = exercise.copy(state = state)
        dao.update(updatedExercise)
        src.uploadTrainingExercise(id)
    }
    
    suspend fun updateIndexNumbers(trainingExercises: List<TrainingExercise>) = dao.updateIndexNumbers(trainingExercises)
    
    suspend fun update(trainingExercises: List<TrainingExercise>) {
        if (trainingExercises.isEmpty()) return
        
        dao.update(trainingExercises)
        src.uploadTrainingExerciseList(trainingExercises[0].trainingId)
    }
    
    suspend fun delete(id: String) {
        dao.delete(id)
        src.deleteTrainingExercise(id)
    }
}
package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.remote.data_sources.TrainingSource

class TrainingExerciseRepository(private val dao: TrainingExerciseDao, private val src: TrainingSource) {
    
    fun trainingExercisesFlow(trainingId: Long) = dao.trainingExercisesFlow(trainingId)
    
    suspend fun previousTrainingExercise(trainingId: Long, exercise: String) = dao.previousTrainingExercise(trainingId, exercise)
    
    fun trainingExerciseFlow(id: Long) = dao.trainingExerciseFlow(id)
    
    suspend fun trainingExercise(id: Long) = dao.trainingExercise(id)
    
    suspend fun trainingExercises(trainingId: Long) = dao.trainingExercises(trainingId)
    
    suspend fun insert(exercise: TrainingExercise) {
        val id = dao.insert(exercise)
        src.uploadTrainingExercise(id)
    }
    
    suspend fun insert(exercises: List<TrainingExercise>) {
        dao.insert(exercises)
        src.uploadTrainingExerciseList(exercises[0].trainingId)
    }
    
    suspend fun delete(id: Long) = dao.delete(id)
    
    suspend fun updateState(id: Long, state: Int) {
        val exercise = dao.trainingExercise(id)
        val updatedExercise = exercise.copy(state = state)
        dao.update(updatedExercise)
        src.uploadTrainingExercise(id)
    }
    
    suspend fun updateIndexNumbers(trainingExercises: List<TrainingExercise>) = dao.updateIndexNumbers(trainingExercises)
    
    suspend fun update(trainingExercises: List<TrainingExercise>) {
        dao.update(trainingExercises)
        src.uploadTrainingExerciseList(trainingExercises[0].trainingId)
    }
}
package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.local.relation.DetailedTrainingExercise

class TrainingExerciseRepository(private val dao: TrainingExerciseDao) {
    
    fun detailedTrainingExercisesFlow(trainingId: Long) = dao.detailedTrainingExercisesFlow(trainingId)
    
    suspend fun previousTrainingExercise(trainingId: Long, exercise: String) = dao.previousTrainingExercise(trainingId, exercise)
    
    fun trainingExerciseFlow(id: Long) = dao.trainingExerciseFlow(id)
    
    suspend fun trainingExercise(id: Long) = dao.trainingExercise(id)
    
    suspend fun insert(exercise: TrainingExercise) = dao.insert(exercise)
    
    suspend fun delete(id: Long) = dao.delete(id)
    
    suspend fun fillTrainingWithProgramExercises(trainingId: Long, programDayId: Long) = dao.fillTrainingWithProgramExercises(trainingId, programDayId)
    
    suspend fun updateState(id: Long, state: Int) = dao.updateState(id, state)
    
    suspend fun updateIndexNumbers(detailedTrainingExercises: List<DetailedTrainingExercise>) = dao.updateIndexNumbers(detailedTrainingExercises)
    
    suspend fun finishTrainingExercises(trainingId: Long) =
            dao.finishTrainingExercises(
                    trainingId,
                    TrainingExercise.FINISHED,
                    TrainingExercise.RUNNING
            )
}
package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise

class TrainingExerciseRepository(private val trainingExerciseDao: TrainingExerciseDao) {
    
    suspend fun detailedTrainingExercises(trainingId: Long) =
            trainingExerciseDao.detailedTrainingExercises(trainingId)
    
    suspend fun previousTrainingExercise(trainingId: Long, exercise: String) =
            trainingExerciseDao.previousTrainingExercise(trainingId, exercise)
    
    suspend fun trainingExercise(id: Long) =
            trainingExerciseDao.trainingExercise(id)
    
    suspend fun insert(exercise: TrainingExercise) =
            trainingExerciseDao.insert(exercise)
    
    suspend fun delete(id: Long) =
            trainingExerciseDao.delete(id)
    
    suspend fun fillTrainingWithProgramExercises(trainingId: Long, programDayId: Long) =
            trainingExerciseDao.fillTrainingWithProgramExercises(trainingId, programDayId)
    
    suspend fun updateState(id: Long, state: Int) =
            trainingExerciseDao.updateState(id, state)
    
    suspend fun updateIndexNumbers(detailedTrainingExercises: List<DetailedTrainingExercise>) =
            trainingExerciseDao.updateIndexNumbers(detailedTrainingExercises)
    
    suspend fun finishTrainingExercises(trainingId: Long) =
            trainingExerciseDao.finishTrainingExercises(
                    trainingId,
                    TrainingExercise.FINISHED,
                    TrainingExercise.RUNNING
            )
}
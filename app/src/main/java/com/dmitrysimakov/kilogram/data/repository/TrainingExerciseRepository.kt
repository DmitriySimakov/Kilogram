package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise

class TrainingExerciseRepository(private val trainingExerciseDao: TrainingExerciseDao) {
    
    fun loadDetailedTrainingExerciseList(trainingId: Long) = trainingExerciseDao.getDetailedTrainingExerciseList(trainingId)
    
    fun loadPrevTrainingExercise(trainingId: Long, exercise: String)
            = trainingExerciseDao.getPrevTrainingExercise(trainingId, exercise)
    
    fun loadTrainingExercise(id: Long) = trainingExerciseDao.getTrainingExercise(id)
    
    suspend fun addExercise(exercise: TrainingExercise) {
        trainingExerciseDao.insert(exercise)
    }
    
    suspend fun deleteExercise(exercise: DetailedTrainingExercise) {
        trainingExerciseDao.deleteExerciseFromTraining(exercise._id)
    }
    
    suspend fun fillTrainingWithProgramExercises(trainingId: Long, programDayId: Long) {
        trainingExerciseDao.fillTrainingWithProgramExercises(trainingId, programDayId)
    }
    
    suspend fun updateState(id: Long, state: Int) {
        trainingExerciseDao.updateState(id, state)
    }
    
    suspend fun updateIndexNumbers(items: List<DetailedTrainingExercise>) {
        trainingExerciseDao.updateIndexNumbers(items)
    }
    
    suspend fun finishTrainingExercises(trainingId: Long) {
        trainingExerciseDao.finishTrainingExercises(trainingId, TrainingExercise.FINISHED, TrainingExercise.RUNNING)
    }
}
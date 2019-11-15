package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrainingExerciseRepository(
        private val trainingExerciseDao: TrainingExerciseDao,
        private val io: CoroutineDispatcher = Dispatchers.IO
) {
    
    suspend fun loadDetailedTrainingExerciseList(trainingId: Long) = withContext(io) {
        trainingExerciseDao.getDetailedTrainingExerciseList(trainingId)
    }
    
    suspend fun loadPrevTrainingExercise(trainingId: Long, exercise: String) = withContext(io) {
        trainingExerciseDao.getPrevTrainingExercise(trainingId, exercise)
    }
    
    suspend fun loadTrainingExercise(id: Long) = withContext(io) {
        trainingExerciseDao.getTrainingExercise(id)
    }
    
    suspend fun addExercise(exercise: TrainingExercise) = withContext(io) {
        trainingExerciseDao.insert(exercise)
    }
    
    suspend fun deleteExercise(exercise: DetailedTrainingExercise) = withContext(io) {
        trainingExerciseDao.deleteExerciseFromTraining(exercise._id)
    }
    
    suspend fun fillTrainingWithProgramExercises(trainingId: Long, programDayId: Long) = withContext(io) {
        trainingExerciseDao.fillTrainingWithProgramExercises(trainingId, programDayId)
    }
    
    suspend fun updateState(id: Long, state: Int) = withContext(io) {
        trainingExerciseDao.updateState(id, state)
    }
    
    suspend fun updateIndexNumbers(items: List<DetailedTrainingExercise>) = withContext(io) {
        trainingExerciseDao.updateIndexNumbers(items)
    }
    
    suspend fun finishTrainingExercises(trainingId: Long) = withContext(io) {
        trainingExerciseDao.finishTrainingExercises(trainingId, TrainingExercise.FINISHED, TrainingExercise.RUNNING)
    }
}
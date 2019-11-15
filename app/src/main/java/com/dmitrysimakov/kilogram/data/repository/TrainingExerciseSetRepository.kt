package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseSetDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExerciseSet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrainingExerciseSetRepository(
        private val trainingExerciseSetDao: TrainingExerciseSetDao,
        private val io: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun loadSets(training_exercise_id: Long) = withContext(io) {
        trainingExerciseSetDao.getSets(training_exercise_id)
    }
    
    suspend fun loadSet(id: Long) = withContext(io) {
        trainingExerciseSetDao.getSet(id)
    }
    
    suspend fun insertSet(set: TrainingExerciseSet) = withContext(io) {
        trainingExerciseSetDao.insert(set)
    }
    
    suspend fun updateSet(set: TrainingExerciseSet) = withContext(io) {
        trainingExerciseSetDao.update(set)
    }
    
    suspend fun deleteSet(id: Long) = withContext(io) {
        trainingExerciseSetDao.delete(id)
    }
}
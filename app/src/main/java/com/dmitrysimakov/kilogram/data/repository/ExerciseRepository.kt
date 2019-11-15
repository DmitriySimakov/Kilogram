package com.dmitrysimakov.kilogram.data.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.local.dao.*
import com.dmitrysimakov.kilogram.data.local.entity.Exercise
import kotlinx.coroutines.*

class ExerciseRepository(
        private val exerciseDao: ExerciseDao,
        private val io: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun loadExerciseList(query: SupportSQLiteQuery) =  exerciseDao.getExerciseList(query)

    suspend fun loadExercise(name: String) = withContext(io) {
        exerciseDao.getExercise(name)
    }
    
    suspend fun updateExercise(exercise: Exercise) = withContext(io) {
        exerciseDao.update(exercise)
    }
    
    suspend fun setFavorite(name: String, isFavorite: Boolean) = withContext(io) {
        exerciseDao.setFavorite(name, isFavorite)
    }
    
    suspend fun increaseExecutionsCnt(name: String) = withContext(io) {
        exerciseDao.increaseExecutionsCnt(name)
    }
    
    suspend fun decreaseExecutionsCnt(name: String) = withContext(io) {
        exerciseDao.decreaseExecutionsCnt(name)
    }
}

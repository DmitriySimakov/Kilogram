package com.dmitrysimakov.kilogram.data.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.local.dao.ExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.Exercise

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    suspend fun loadExerciseList(query: SupportSQLiteQuery) =  exerciseDao.getExerciseList(query)

    suspend fun loadExercise(name: String) = exerciseDao.getExercise(name)
    
    suspend fun updateExercise(exercise: Exercise) {
        exerciseDao.update(exercise)
    }
    
    suspend fun setFavorite(name: String, isFavorite: Boolean) {
        exerciseDao.setFavorite(name, isFavorite)
    }
    
    suspend fun increaseExecutionsCnt(name: String) {
        exerciseDao.increaseExecutionsCnt(name)
    }
    
    suspend fun decreaseExecutionsCnt(name: String) {
        exerciseDao.decreaseExecutionsCnt(name)
    }
}

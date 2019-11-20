package com.dmitrysimakov.kilogram.data.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.local.dao.ExerciseDao

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    fun exercisesFlow(query: SupportSQLiteQuery) =
            exerciseDao.exercisesFlow(query)

    suspend fun measures(exerciseName: String) =
            exerciseDao.measures(exerciseName)
    
    suspend fun exercise(name: String) =
            exerciseDao.exercise(name)
    
    suspend fun setFavorite(name: String, isFavorite: Boolean)
            = exerciseDao.setFavorite(name, isFavorite)
    
    suspend fun increaseExecutionsCnt(name: String) =
            exerciseDao.increaseExecutionsCnt(name)
    
    suspend fun decreaseExecutionsCnt(name: String) =
            exerciseDao.decreaseExecutionsCnt(name)
}

package com.dmitrysimakov.kilogram.data.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.local.dao.ExerciseDao

class ExerciseRepository(private val dao: ExerciseDao) {

    fun exercisesFlow(query: SupportSQLiteQuery) = dao.exercisesFlow(query)

    suspend fun measures(exerciseName: String) = dao.measures(exerciseName)
    
    suspend fun exercise(name: String) = dao.exercise(name)
    
    suspend fun setFavorite(name: String, isFavorite: Boolean) = dao.setFavorite(name, isFavorite)
    
    suspend fun increaseExecutionsCnt(name: String) = dao.increaseExecutionsCnt(name)
    
    suspend fun decreaseExecutionsCnt(name: String) = dao.decreaseExecutionsCnt(name)
}

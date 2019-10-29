package com.dmitrysimakov.kilogram.data.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.local.dao.*
import com.dmitrysimakov.kilogram.data.local.entity.Exercise
import com.dmitrysimakov.kilogram.util.AppExecutors

class ExerciseRepository(
        private val executors: AppExecutors,
        private val exerciseDao: ExerciseDao,
        private val muscleDao: MuscleDao,
        private val mechanicsTypeDao: MechanicsTypeDao,
        private val exerciseTypeDao: ExerciseTypeDao,
        private val equipmentDao: EquipmentDao
) {

    fun loadExerciseList(query: SupportSQLiteQuery) = exerciseDao.getExerciseList(query)

    fun loadExercise(id: Long) = exerciseDao.getExercise(id)
    
    fun loadDetailedExerciseR(id: Long) = exerciseDao.getDetailedExerciseR(id)
    
    fun updateExercise(exercise: Exercise) {
        executors.diskIO().execute {
            exerciseDao.update(exercise)
        }
    }
    
    fun setFavorite(id: Long, isFavorite: Boolean) {
        executors.diskIO().execute {
            exerciseDao.setFavorite(id, isFavorite)
        }
    }
    
    fun increaseExecutionsCnt(id: Long) {
        executors.diskIO().execute {
            exerciseDao.increaseExecutionsCnt(id)
        }
    }
    
    fun decreaseExecutionsCnt(id: Long) {
        executors.diskIO().execute {
            exerciseDao.decreaseExecutionsCnt(id)
        }
    }
    
    
    fun loadMuscleParams() = muscleDao.getParams()
    
    fun loadMuscleParams(id: Long) = muscleDao.getProgramDayParams(id)
    
    fun loadMechanicsTypeParams() = mechanicsTypeDao.getParams()
    
    fun loadExerciseTypeParams() = exerciseTypeDao.getParams()
    
    fun loadEquipmentParams() = equipmentDao.getParams()
}

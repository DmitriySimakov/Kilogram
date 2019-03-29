package com.dmitrysimakov.kilogram.data.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.dao.*
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.util.AppExecutors
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
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
            exerciseDao.updateExercise(exercise)
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
    
    fun loadMechanicsTypeParams() = mechanicsTypeDao.getParams()
    
    fun loadExerciseTypeParams() = exerciseTypeDao.getParams()
    
    fun loadEquipmentParams() = equipmentDao.getParams()
}

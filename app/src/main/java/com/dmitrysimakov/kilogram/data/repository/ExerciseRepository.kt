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
        private val equipmentDao: EquipmentDao,
        private val targetedMuscleDao: TargetedMuscleDao
) {

    fun loadExerciseList(query: SupportSQLiteQuery) = exerciseDao.getExerciseList(query)

    fun loadExercise(name: String) = exerciseDao.getExercise(name)
    
    fun loadTargetedMuscles(exerciseName: String) = targetedMuscleDao.getTargetedMuscles(exerciseName)
    
    fun updateExercise(exercise: Exercise) {
        executors.diskIO().execute {
            exerciseDao.update(exercise)
        }
    }
    
    fun setFavorite(name: String, isFavorite: Boolean) {
        executors.diskIO().execute {
            exerciseDao.setFavorite(name, isFavorite)
        }
    }
    
    fun increaseExecutionsCnt(name: String) {
        executors.diskIO().execute {
            exerciseDao.increaseExecutionsCnt(name)
        }
    }
    
    fun decreaseExecutionsCnt(name: String) {
        executors.diskIO().execute {
            exerciseDao.decreaseExecutionsCnt(name)
        }
    }
    
    
    fun loadMuscleParams() = muscleDao.getParamList()
    
    fun loadMuscleParams(id: Long) = muscleDao.getProgramDayParamList(id)
    
    fun loadMechanicsTypeParams() = mechanicsTypeDao.getParamList()
    
    fun loadExerciseTypeParams() = exerciseTypeDao.getParamList()
    
    fun loadEquipmentParams() = equipmentDao.getParamList()
}

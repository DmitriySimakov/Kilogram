package com.dmitrysimakov.kilogram.data.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.local.dao.*
import com.dmitrysimakov.kilogram.data.local.entity.Exercise

class ExerciseRepository(
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
    
    
    fun loadMuscleParams() = muscleDao.getParamList()
    
    fun loadMuscleParams(id: Long) = muscleDao.getProgramDayParamList(id)
    
    fun loadMechanicsTypeParams() = mechanicsTypeDao.getParamList()
    
    fun loadExerciseTypeParams() = exerciseTypeDao.getParamList()
    
    fun loadEquipmentParams() = equipmentDao.getParamList()
}

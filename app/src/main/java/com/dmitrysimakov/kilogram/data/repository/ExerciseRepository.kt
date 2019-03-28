package com.dmitrysimakov.kilogram.data.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.dao.EquipmentDao
import com.dmitrysimakov.kilogram.data.dao.ExerciseDao
import com.dmitrysimakov.kilogram.data.dao.ExerciseTypeDao
import com.dmitrysimakov.kilogram.data.dao.MechanicsTypeDao
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.util.AppExecutors
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
        private val executors: AppExecutors,
        private val exerciseDao: ExerciseDao,
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
    
    
    fun loadMechanicsTypeList() = mechanicsTypeDao.getList()
    
    fun loadExerciseTypeList() = exerciseTypeDao.getList()
    
    fun loadEquipmentList() = equipmentDao.getList()
}

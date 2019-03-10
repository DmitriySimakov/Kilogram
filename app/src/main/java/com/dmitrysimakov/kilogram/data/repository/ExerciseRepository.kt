package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.dao.ExerciseDao
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.util.AppExecutors
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
        private val executors: AppExecutors,
        private val exerciseDao: ExerciseDao
) {

    fun loadExerciseList(muscleId: Long) = exerciseDao.getExerciseList(muscleId)

    fun loadExercise(id: Long) = exerciseDao.getExercise(id)
    
    fun loadDetailedExerciseR(id: Long) = exerciseDao.getDetailedExerciseR(id)
    
    fun updateExercise(exercise: Exercise) {
        executors.diskIO().execute {
            exerciseDao.updateExercise(exercise)
        }
    }
}

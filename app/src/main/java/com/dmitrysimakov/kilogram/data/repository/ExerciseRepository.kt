package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.dao.ExerciseDao
import javax.inject.Inject

class ExerciseRepository @Inject constructor(private val exerciseDao: ExerciseDao) {

    fun loadExerciseList(muscleId: Long) = exerciseDao.getExerciseList(muscleId)

    fun loadExerciseR(id: Long) = exerciseDao.getExerciseR(id)
}

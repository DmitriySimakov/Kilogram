package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.MuscleDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MuscleRepository @Inject constructor(private val muscleDao: MuscleDao) {

    fun loadMuscleList() = muscleDao.getMuscleList()
}
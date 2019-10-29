package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.MuscleDao

class MuscleRepository(private val muscleDao: MuscleDao) {

    fun loadMuscleList() = muscleDao.getMuscleList()
}
package com.dmitrysimakov.kilogram.ui.exercises.choose_muscle

import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.local.dao.MuscleDao

class ChooseMuscleViewModel (muscleDao: MuscleDao) : ViewModel() {

    val muscleList = muscleDao.getMuscleList()
}
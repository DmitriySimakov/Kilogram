package com.dmitrysimakov.kilogram.ui.catalogs.exercises.exercise_targets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dmitrysimakov.kilogram.data.local.dao.ExerciseTargetDao

class ExerciseTargetsViewModel (exerciseTargetDao: ExerciseTargetDao) : ViewModel() {
    
    val exerciseTargets = liveData { emit(exerciseTargetDao.exerciseTargets()) }
}
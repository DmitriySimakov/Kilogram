package com.dmitrysimakov.kilogram.ui.exercises

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.dao.TargetedMuscleDao
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import kotlinx.coroutines.launch

class DetailedExerciseViewModel (
        private val targetedMuscleDao: TargetedMuscleDao,
        private val repository: ExerciseRepository
) : ViewModel() {
    
    val exerciseName = MutableLiveData<String>()
    
    val exercise = exerciseName.switchMap { liveData { emit(repository.exercise(it)) } }
    
    val targetedMuscles = exerciseName.switchMap { liveData {
        emit(targetedMuscleDao.targetedMuscles(it).joinToString(", ")) }
    }
    
    fun setFavorite(isChecked: Boolean) = viewModelScope.launch {
        exercise.value?.let { repository.setFavorite(it.name, isChecked) }
    }
}
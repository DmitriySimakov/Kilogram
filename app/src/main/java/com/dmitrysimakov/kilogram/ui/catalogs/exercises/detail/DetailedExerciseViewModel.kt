package com.dmitrysimakov.kilogram.ui.catalogs.exercises.detail

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.dao.TargetedMuscleDao
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch

class DetailedExerciseViewModel (
        private val targetedMuscleDao: TargetedMuscleDao,
        private val repository: ExerciseRepository
) : ViewModel() {
    
    private val _exerciseName = MutableLiveData<String>()
    
    val exercise = _exerciseName.switchMap { liveData { emit(repository.exercise(it)) } }
    
    val targetedMuscles = _exerciseName.switchMap { liveData {
        emit(targetedMuscleDao.targetedMuscles(it).joinToString(", ")) }
    }
    
    fun setExerciseName(name: String) = _exerciseName.setNewValue(name)
    
    fun setFavorite(isChecked: Boolean) = viewModelScope.launch {
        exercise.value?.let { repository.setFavorite(it.name, isChecked) }
    }
}
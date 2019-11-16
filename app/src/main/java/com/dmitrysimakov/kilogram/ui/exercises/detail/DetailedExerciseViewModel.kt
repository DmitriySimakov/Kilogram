package com.dmitrysimakov.kilogram.ui.exercises.detail

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.dao.TargetedMuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.Exercise
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import kotlinx.coroutines.launch

class DetailedExerciseViewModel (
        private val targetedMuscleDao: TargetedMuscleDao,
        private val repository: ExerciseRepository
) : ViewModel() {
    
    private val _exercise = MutableLiveData<Exercise>()
    val exercise: LiveData<Exercise> = _exercise
    
    private val _targetedMuscles = MutableLiveData<String>()
    val targetedMuscles: LiveData<String> = _targetedMuscles
    
    fun start(exerciseName: String) = viewModelScope.launch {
        _exercise.value = repository.exercise(exerciseName)
        _targetedMuscles.value = targetedMuscleDao.targetedMuscles(exerciseName).joinToString(", ")
    }
    
    fun setFavorite(isChecked: Boolean) = viewModelScope.launch {
        exercise.value?.let { repository.setFavorite(it.name, isChecked) }
    }
}
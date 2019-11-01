package com.dmitrysimakov.kilogram.ui.exercises.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch

class DetailedExerciseViewModel (private val repository: ExerciseRepository) : ViewModel() {
    
    private val _exerciseName = MutableLiveData<String>()
    fun setExerciseName(name: String?) {
        _exerciseName.setNewValue(name)
    }

    val exercise = _exerciseName.switchMap {
        repository.loadExercise(it)
    }
    
    val targetedMuscles = _exerciseName.switchMap { // TODO
        repository.loadTargetedMuscles(it)
    }
    
    fun setFavorite(isChecked: Boolean) { viewModelScope.launch {
        exercise.value?.let {
            repository.setFavorite(it.name, isChecked)
        }
    }}
}
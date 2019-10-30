package com.dmitrysimakov.kilogram.ui.exercises.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.util.setNewValue

class DetailedExerciseViewModel (private val repository: ExerciseRepository) : ViewModel() {
    
    private val _exerciseName = MutableLiveData<String>()
    fun setExerciseName(name: String?) {
        _exerciseName.setNewValue(name)
    }

    val exercise = Transformations.switchMap(_exerciseName) {
        repository.loadExercise(it)
    }
    
    val targetedMuscles = Transformations.switchMap(_exerciseName) {
        repository.loadTargetedMuscles(it)
    }
    
    fun setFavorite(isChecked: Boolean) {
        exercise.value?.let {
            repository.setFavorite(it.name, isChecked)
        }
    }
}
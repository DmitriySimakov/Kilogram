package com.dmitrysimakov.kilogram.ui.training.addExercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import javax.inject.Inject

class AddExerciseViewModel @Inject constructor(
        private val repository: TrainingRepository
) : ViewModel() {

    private val _exercise = MutableLiveData<Exercise?>()
    val exercise: LiveData<Exercise?>
        get() = _exercise

    fun addExercise(exercise_id: Long, training_id: Long) {
        repository.addExercise(exercise_id, training_id)
    }
}
package com.dmitrysimakov.kilogram.ui.training.add_exercise

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import javax.inject.Inject

class AddExerciseViewModel @Inject constructor(
        private val trainingRepository: TrainingRepository,
        private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val _exerciseId = MutableLiveData<Long>()
    
    val exercise = Transformations.switchMap(_exerciseId) { id ->
        if (id == null) {
            AbsentLiveData.create()
        } else {
            exerciseRepository.loadExercise(id)
        }
    }

    fun setExercise(id: Long?) {
        if (_exerciseId.value != id) {
            _exerciseId.value = id
        }
    }
    
    fun addExercise(exercise_id: Long, training_id: Long) {
        trainingRepository.addExercise(exercise_id, training_id)
    }
}
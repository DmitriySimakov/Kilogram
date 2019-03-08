package com.dmitrysimakov.kilogram.ui.trainings.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import java.util.HashSet
import javax.inject.Inject

class TrainingExercisesViewModel @Inject constructor(private val repository: TrainingRepository) : ViewModel() {

    private val _trainingId = MutableLiveData<Long>()
    
    val training = Transformations.switchMap(_trainingId) {
        repository.loadTraining(it)
    }
    
    val exercises = Transformations.switchMap(_trainingId) {
        repository.loadExercises(it)
    }
    
    fun setTraining(id: Long) {
        _trainingId.setNewValue(id)
    }

    fun deleteExercise(exercise: TrainingExerciseR) {
        repository.deleteExercise(exercise)
    }
    
    fun finishSession() {
        training.value?.let {
            it.duration = 10 // TODO
            repository.updateTraining(it)
        }
    }
}
package com.dmitrysimakov.kilogram.ui.trainings.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import com.dmitrysimakov.kilogram.util.setNewValue
import javax.inject.Inject

class TrainingExercisesViewModel @Inject constructor(private val repository: TrainingRepository) : ViewModel() {

    private val _trainingId = MutableLiveData<Long>()
    private val _programDayId = MutableLiveData<Long>()
    
    val training = Transformations.switchMap(_trainingId) { id ->
        when(id) {
            null -> AbsentLiveData.create()
            else -> repository.loadTraining(id)
        }
    }
    
    val exercises = Transformations.switchMap(_trainingId) { id ->
        when (id) {
            null ->AbsentLiveData.create()
            else ->repository.loadExercises(id)
        }
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
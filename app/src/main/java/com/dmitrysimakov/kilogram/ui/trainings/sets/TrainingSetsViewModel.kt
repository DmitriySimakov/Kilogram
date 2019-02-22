package com.dmitrysimakov.kilogram.ui.trainings.sets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.TrainingExerciseSet
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import com.dmitrysimakov.kilogram.util.setNewValue
import javax.inject.Inject

class TrainingSetsViewModel @Inject constructor(private val repository: TrainingRepository) : ViewModel() {

    private val _trainingExerciseId = MutableLiveData<Long>()

    val sets = Transformations.switchMap(_trainingExerciseId) { id ->
        when (id) {
            null -> AbsentLiveData.create()
            else -> repository.loadSets(id)
        }
    }
    
    val exerciseMeasures = Transformations.switchMap(_trainingExerciseId) { id ->
        when (id) {
            null -> AbsentLiveData.create()
            else -> repository.loadExerciseMeasures(id)
        }
    }

    fun setTrainingExercise(id: Long) {
        _trainingExerciseId.setNewValue(id)
    }

    fun deleteSet(set: TrainingExerciseSet) {
        repository.deleteSet(set)
    }
}
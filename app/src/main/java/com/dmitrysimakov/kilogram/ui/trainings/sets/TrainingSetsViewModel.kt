package com.dmitrysimakov.kilogram.ui.trainings.sets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.TrainingExerciseSet
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import javax.inject.Inject

class TrainingSetsViewModel @Inject constructor(private val repository: TrainingRepository) : ViewModel() {

    private val _trainingExerciseId = MutableLiveData<Long>()
    private val _exerciseId = MutableLiveData<Long>()

    val sets = Transformations.switchMap(_trainingExerciseId) { id ->
        when (id) {
            null -> AbsentLiveData.create()
            else -> repository.loadSets(id)
        }
    }
    
    val exerciseMeasures = Transformations.switchMap(_exerciseId) { id ->
        when (id) {
            null -> AbsentLiveData.create()
            else -> repository.loadExerciseMeasures(id)
        }
    }

    fun setParams(trainingExerciseId: Long, exerciseId: Long) {
        if (_trainingExerciseId.value != trainingExerciseId) {
            _trainingExerciseId.value = trainingExerciseId
        }
        if (_exerciseId.value != exerciseId) {
            _exerciseId.value = exerciseId
        }
    }

    fun deleteSet(set: TrainingExerciseSet) {
        repository.deleteSet(set)
    }
}
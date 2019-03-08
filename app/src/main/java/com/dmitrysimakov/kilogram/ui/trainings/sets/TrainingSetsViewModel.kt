package com.dmitrysimakov.kilogram.ui.trainings.sets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.TrainingExerciseSet
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseSetRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import javax.inject.Inject

class TrainingSetsViewModel @Inject constructor(
        private val trainingExerciseSetRepository: TrainingExerciseSetRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository
) : ViewModel() {

    private val _trainingExerciseId = MutableLiveData<Long>()

    val sets = Transformations.switchMap(_trainingExerciseId) {
        trainingExerciseSetRepository.loadSets(it)
    }
    
    val exerciseMeasures = Transformations.switchMap(_trainingExerciseId) {
        trainingExerciseRepository.loadExerciseMeasures(it)
    }

    fun setTrainingExercise(id: Long) {
        _trainingExerciseId.setNewValue(id)
    }

    fun deleteSet(set: TrainingExerciseSet) {
        trainingExerciseSetRepository.deleteSet(set)
    }
}
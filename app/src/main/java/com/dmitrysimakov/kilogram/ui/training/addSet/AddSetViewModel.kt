package com.dmitrysimakov.kilogram. ui.training.addSet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.TrainingExerciseSet
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import javax.inject.Inject

class AddSetViewModel @Inject constructor(
        private val repository: TrainingRepository
) : ViewModel() {

    fun addSet(trainingExerciseId: Long, weight: Int, reps: Int, time: Int, distance: Int) {
        repository.insertSet(TrainingExerciseSet(0, trainingExerciseId, null, weight, reps, time, distance))
    }
    
    fun updateSet(set: TrainingExerciseSet) {
        repository.updateSet(set)
    }
}

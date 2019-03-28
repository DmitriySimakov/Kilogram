package com.dmitrysimakov.kilogram. ui.trainings.add_set

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.entity.TrainingExerciseSet
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseSetRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import javax.inject.Inject

class AddSetViewModel @Inject constructor(
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val trainingExerciseSetRepository: TrainingExerciseSetRepository
) : ViewModel() {
    
    fun setTrainingExercise(id: Long) { _trainingExerciseId.setNewValue(id) }
    private val _trainingExerciseId = MutableLiveData<Long>()
    val trainingExercise = Transformations.switchMap(_trainingExerciseId) {
        trainingExerciseRepository.loadTrainingExercise(it)
    }
    
    fun setSet(id: Long) { _setId.setNewValue(id) }
    private val _setId = MutableLiveData<Long>()
    val set = Transformations.switchMap(_setId) {
        when (it) {
            0L -> MutableLiveData(TrainingExerciseSet(0, _trainingExerciseId.value!!, 0, 0, 0, 0, 0))
            else -> trainingExerciseSetRepository.loadSet(it)
        }
    }
    
    fun addSet(secsSinceStart: Int) {
        set.value?.let {
            it.secs_since_start = secsSinceStart
            trainingExerciseSetRepository.insertSet(it)
            trainingExerciseRepository.updateState(it.training_exercise_id, TrainingExercise.RUNNING)
        }
    }
    
    fun updateSet() {
        set.value?.let { trainingExerciseSetRepository.updateSet(it) }
    }
    
    fun decreaseWeight() { set.value?.weight = (set.value?.weight ?: 0) - 5 }
    
    fun increaseWeight() { set.value?.weight = (set.value?.weight ?: 0) + 5 }
    
    fun decreaseReps() { set.value?.reps = (set.value?.reps ?: 0) - 1 }
    
    fun increaseReps() { set.value?.reps = (set.value?.reps ?: 0) + 1 }
    
    fun decreaseTime() { set.value?.time = (set.value?.time ?: 0) - 15 }
    
    fun increaseTime() { set.value?.time = (set.value?.time ?: 0) + 15 }
    
    fun decreaseDistance() { set.value?.distance = (set.value?.distance ?: 0) - 100 }
    
    fun increaseDistance() { set.value?.distance = (set.value?.distance ?: 0) + 100 }
}

package com.dmitrysimakov.kilogram. ui.trainings.add_set

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExerciseSet
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseSetRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch

class AddSetViewModel(
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val trainingExerciseSetRepository: TrainingExerciseSetRepository
) : ViewModel() {
    
    fun setTrainingExercise(id: Long) { _trainingExerciseId.setNewValue(id) }
    private val _trainingExerciseId = MutableLiveData<Long>()
    val trainingExercise = _trainingExerciseId.switchMap {
        trainingExerciseRepository.loadTrainingExercise(it)
    }
    
    private var weight = 0
    private var reps = 0
    private var time = 0
    private var distance = 0
    
    fun setSet(id: Long, weight: Int, reps: Int, time: Int, distance: Int) {
        this.weight = weight
        this.reps = reps
        this.time = time
        this.distance = distance
        
        _setId.setNewValue(id)
    }
    private val _setId = MutableLiveData<Long>()
    val set = _setId.switchMap {
        when (it) {
            0L -> MutableLiveData(TrainingExerciseSet(0, _trainingExerciseId.value!!, 0, weight, reps, time, distance))
            else -> trainingExerciseSetRepository.loadSet(it)
        }
    }
    
    fun addSet(secsSinceStart: Int) { viewModelScope.launch {
        set.value?.let {
            it.secs_since_start = secsSinceStart
            trainingExerciseSetRepository.insertSet(it)
            trainingExerciseRepository.updateState(it.training_exercise_id, TrainingExercise.RUNNING)
        }
    }}
    
    fun updateSet() { viewModelScope.launch {
        set.value?.let { trainingExerciseSetRepository.updateSet(it) }
    }}
    
    fun decreaseWeight() { set.value?.weight = (set.value?.weight ?: 0) - 5 }
    
    fun increaseWeight() { set.value?.weight = (set.value?.weight ?: 0) + 5 }
    
    fun decreaseReps() { set.value?.reps = (set.value?.reps ?: 0) - 1 }
    
    fun increaseReps() { set.value?.reps = (set.value?.reps ?: 0) + 1 }
    
    fun decreaseTime() { set.value?.time = (set.value?.time ?: 0) - 15 }
    
    fun increaseTime() { set.value?.time = (set.value?.time ?: 0) + 15 }
    
    fun decreaseDistance() { set.value?.distance = (set.value?.distance ?: 0) - 100 }
    
    fun increaseDistance() { set.value?.distance = (set.value?.distance ?: 0) + 100 }
}

package com.dmitrysimakov.kilogram. ui.trainings.add_set

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExerciseSet
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseSetRepository
import com.dmitrysimakov.kilogram.util.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddSetViewModel(
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val trainingExerciseSetRepository: TrainingExerciseSetRepository
) : ViewModel() {
    
    private val _trainingExercise = MutableLiveData<TrainingExercise>()
    val trainingExercise: LiveData<TrainingExercise> = _trainingExercise
    
    private val _trainingSet = MutableLiveData<TrainingExerciseSet>()
    val trainingSet: LiveData<TrainingExerciseSet> = _trainingSet
    
    private val _trainingSetSavedEvent = MutableLiveData<Event<Unit>>()
    val trainingSetSavedEvent: LiveData<Event<Unit>> = _trainingSetSavedEvent
    
    fun start(trainingExerciseId: Long, setId: Long, weight: Int, reps: Int, time: Int, distance: Int) {
        viewModelScope.launch {
            _trainingExercise.value = trainingExerciseRepository.loadTrainingExercise(trainingExerciseId)
            _trainingSet.value = if (setId == 0L) {
                TrainingExerciseSet(0, trainingExerciseId, weight, reps, time, distance)
            } else {
                trainingExerciseSetRepository.loadSet(setId)
            }
        }
    }
    
    fun addSet(secsSinceStart: Int) { viewModelScope.launch {
        trainingSet.value?.let {
            it.secs_since_start = secsSinceStart
            trainingExerciseSetRepository.insertSet(it)
            if (trainingExercise.value?.state == TrainingExercise.PLANNED) {
                trainingExerciseRepository.updateState(it.training_exercise_id, TrainingExercise.RUNNING)
            }
            _trainingSetSavedEvent.value = Event(Unit)
        }
    }}
    
    fun updateSet() { viewModelScope.launch {
        trainingSet.value?.let {
            trainingExerciseSetRepository.updateSet(it)
            _trainingSetSavedEvent.value = Event(Unit)
        }
    }}
    
    fun decreaseWeight() { trainingSet.value?.weight = (trainingSet.value?.weight ?: 0) - 5 }
    
    fun increaseWeight() { trainingSet.value?.weight = (trainingSet.value?.weight ?: 0) + 5 }
    
    fun decreaseReps() { trainingSet.value?.reps = (trainingSet.value?.reps ?: 0) - 1 }
    
    fun increaseReps() { trainingSet.value?.reps = (trainingSet.value?.reps ?: 0) + 1 }
    
    fun decreaseTime() { trainingSet.value?.time = (trainingSet.value?.time ?: 0) - 15 }
    
    fun increaseTime() { trainingSet.value?.time = (trainingSet.value?.time ?: 0) + 15 }
    
    fun decreaseDistance() { trainingSet.value?.distance = (trainingSet.value?.distance ?: 0) - 100 }
    
    fun increaseDistance() { trainingSet.value?.distance = (trainingSet.value?.distance ?: 0) + 100 }
}

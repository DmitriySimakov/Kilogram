package com.dmitrysimakov.kilogram.ui.home.trainings.add_training_set

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.local.entity.TrainingSet
import com.dmitrysimakov.kilogram.data.local.relation.ExerciseMeasures
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingSetRepository
import com.dmitrysimakov.kilogram.util.Event
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime

class AddTrainingSetViewModel(
        private val exerciseRepository: ExerciseRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val trainingExerciseSetRepository: TrainingSetRepository
) : ViewModel() {
    
    private val _trainingExercise = MutableLiveData<TrainingExercise>()
    val trainingExercise: LiveData<TrainingExercise> = _trainingExercise
    
    private val _measures = MutableLiveData<ExerciseMeasures>()
    val measures: LiveData<ExerciseMeasures> = _measures
    
    private val _trainingSet = MutableLiveData<TrainingSet>()
    val trainingSet: LiveData<TrainingSet> = _trainingSet
    
    private val _trainingSetSavedEvent = MutableLiveData<Event<Unit>>()
    val trainingSetSavedEvent: LiveData<Event<Unit>> = _trainingSetSavedEvent
    
    fun start(trainingExerciseId: Long, setId: Long, weight: Int, reps: Int, time: Int, distance: Int) {
        viewModelScope.launch {
            val trainingExercise = trainingExerciseRepository.trainingExercise(trainingExerciseId)
            _trainingExercise.value = trainingExercise
            _measures.value = exerciseRepository.measures(trainingExercise.exercise)
            _trainingSet.value = if (setId == 0L) {
                TrainingSet(0, trainingExerciseId, weight, reps, time, distance)
            } else {
                trainingExerciseSetRepository.trainingSet(setId)
            }
        }
    }
    
    fun submit() { viewModelScope.launch {
        if (trainingSet.value?.id == 0L) addSet() else updateSet()
    }}
    
    private suspend fun addSet() {
        trainingSet.value?.let {
            it.dateTime = OffsetDateTime.now()
            trainingExerciseSetRepository.insert(it)
            if (trainingExercise.value?.state == TrainingExercise.PLANNED) {
                trainingExerciseRepository.updateState(it.trainingExerciseId, TrainingExercise.RUNNING)
            }
            _trainingSetSavedEvent.value = Event(Unit)
        }
    }
    
    private suspend fun updateSet() {
        trainingSet.value?.let {
            trainingExerciseSetRepository.update(it)
            _trainingSetSavedEvent.value = Event(Unit)
        }
    }
    
    fun decreaseWeight() { trainingSet.value?.weight = (trainingSet.value?.weight ?: 0) - 5 }
    
    fun increaseWeight() { trainingSet.value?.weight = (trainingSet.value?.weight ?: 0) + 5 }
    
    fun decreaseReps() { trainingSet.value?.reps = (trainingSet.value?.reps ?: 0) - 1 }
    
    fun increaseReps() { trainingSet.value?.reps = (trainingSet.value?.reps ?: 0) + 1 }
    
    fun decreaseTime() { trainingSet.value?.time = (trainingSet.value?.time ?: 0) - 15 }
    
    fun increaseTime() { trainingSet.value?.time = (trainingSet.value?.time ?: 0) + 15 }
    
    fun decreaseDistance() { trainingSet.value?.distance = (trainingSet.value?.distance ?: 0) - 100 }
    
    fun increaseDistance() { trainingSet.value?.distance = (trainingSet.value?.distance ?: 0) + 100 }
}

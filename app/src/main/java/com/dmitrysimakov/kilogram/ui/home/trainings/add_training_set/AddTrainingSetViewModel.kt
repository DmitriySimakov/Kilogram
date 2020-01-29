package com.dmitrysimakov.kilogram.ui.home.trainings.add_training_set

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.local.entity.TrainingSet
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingSetRepository
import com.dmitrysimakov.kilogram.util.live_data.Event
import kotlinx.coroutines.launch

class AddTrainingSetViewModel(
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val trainingExerciseSetRepository: TrainingSetRepository
) : ViewModel() {
    
    private val _trainingSetId = MutableLiveData(0L)
    val trainingSetId: LiveData<Long> = _trainingSetId
    
    private val _trainingExercise = MutableLiveData<TrainingExercise>()
    val trainingExercise: LiveData<TrainingExercise> = _trainingExercise
    
    val weight = MutableLiveData<Int>()
    val reps = MutableLiveData<Int>()
    val time = MutableLiveData<Int>()
    val distance = MutableLiveData<Int>()
    
    private val _trainingSetSavedEvent = MutableLiveData<Event<Unit>>()
    val trainingSetSavedEvent: LiveData<Event<Unit>> = _trainingSetSavedEvent
    
    fun start(trainingExerciseId: Long, setId: Long, _weight: Int, _reps: Int, _time: Int, _distance: Int) { viewModelScope.launch {
        _trainingSetId.value = setId
        weight.value = _weight.takeIf { it != -1 }
        reps.value = _reps.takeIf { it != -1 }
        time.value = _time.takeIf { it != -1 }
        distance.value = _distance.takeIf { it != -1 }
        
        _trainingExercise.value = trainingExerciseRepository.trainingExercise(trainingExerciseId)
    }}
    
    fun submit() { viewModelScope.launch {
        val setId = _trainingSetId.value!!
        val exerciseId = trainingExercise.value!!.id
        val trainingSet = TrainingSet(setId, exerciseId, weight.value, reps.value, time.value, distance.value)
        
        if (_trainingSetId.value == 0L) addSet(trainingSet) else updateSet(trainingSet)
    }}
    
    private suspend fun addSet(trainingSet: TrainingSet) {
        trainingExerciseSetRepository.insert(trainingSet)
        if (trainingExercise.value?.state == TrainingExercise.PLANNED) {
            trainingExerciseRepository.updateState(trainingSet.trainingExerciseId, TrainingExercise.RUNNING)
        }
        
        _trainingSetSavedEvent.value = Event(Unit)
    }
    
    private suspend fun updateSet(trainingSet: TrainingSet) {
        trainingExerciseSetRepository.update(trainingSet)
        
        _trainingSetSavedEvent.value = Event(Unit)
    }
    
    fun decreaseWeight() { weight.value = weight.decrease(5, 0) }
    
    fun increaseWeight() { weight.value = weight.increase(5, 10_000) }
    
    fun decreaseReps() { reps.value = reps.decrease(1, 0) }
    
    fun increaseReps() { reps.value = reps.increase(1, 1_000) }
    
    fun decreaseTime() { time.value = time.decrease(15, 0) }
    
    fun increaseTime() { time.value = time.increase(15, 24 * 60 * 60) }
    
    fun decreaseDistance() { distance.value = distance.decrease(100, 0) }
    
    fun increaseDistance() { distance.value = distance.decrease(100, 100_000) }
    
    private fun LiveData<Int>.decrease(subtrahend: Int, minValue: Int)
            = (value!! - subtrahend).coerceAtLeast(minValue)
    
    private fun LiveData<Int>.increase(term: Int, maxValue: Int)
            = (value!! + term).coerceAtMost(maxValue)
}

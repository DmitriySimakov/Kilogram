package com.dmitrysimakov.kilogram.ui.home.trainings.exercises

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.model.TrainingExercise
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.live_data.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrainingExercisesViewModel(
        private val trainingRepository: TrainingRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val exerciseRepository: ExerciseRepository
) : ViewModel() {
    
    val trainingId = MutableLiveData<String>()
    
    val training = trainingId.switchMap { liveData { emit(trainingRepository.training(it)) } }
    
    private val exercises = trainingId.switchMap {
        trainingExerciseRepository.trainingExercisesFlow(it).asLiveData()
    }
    
    val runningExercises = exercises.map {
        it.filter { exercise -> exercise.state == TrainingExercise.RUNNING }
    }
    
    val plannedExercises = exercises.map {
        it.filter { exercise -> exercise.state == TrainingExercise.PLANNED }
    }
    
    val finishedExercises = exercises.map {
        it.filter { exercise -> exercise.state == TrainingExercise.FINISHED }
    }
    
    val trainingFinishedEvent = MutableLiveData<Event<Unit>>()
    
    fun deleteExercise(exercise: TrainingExercise) = viewModelScope.launch {
        trainingExerciseRepository.delete(exercise.id)
        exerciseRepository.decreaseExecutionsCnt(exercise.exercise)
    }
    
    fun finishTraining(duration: Int)  { viewModelScope.launch {
        val training = training.value ?: return@launch
        
        trainingRepository.update(training.copy(duration = duration))
        
        trainingExerciseRepository.update(
                exercises.value!!.map { it.copy(state = TrainingExercise.FINISHED) }
        )
        
        trainingFinishedEvent.value = Event(Unit)
    }}
    
    fun finishExercise(exercise: TrainingExercise) = viewModelScope.launch {
        trainingExerciseRepository.updateState(exercise.id, TrainingExercise.FINISHED)
        exerciseRepository.increaseExecutionsCnt(exercise.exercise)
    }
    
    fun updateIndexNumbers() = CoroutineScope(Dispatchers.IO).launch {
        plannedExercises.value?.let { list ->
            list.forEachIndexed { index, exercise -> exercise.indexNumber = index + 1 }
            trainingExerciseRepository.updateIndexNumbers(list)
        }
    }
}
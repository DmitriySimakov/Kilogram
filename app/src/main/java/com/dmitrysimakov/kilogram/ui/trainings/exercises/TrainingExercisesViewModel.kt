package com.dmitrysimakov.kilogram.ui.trainings.exercises

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.Event
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrainingExercisesViewModel(
        private val trainingRepository: TrainingRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val _training = MutableLiveData<Training>()
    val training: LiveData<Training> = _training
    
    private val _runningExercises = MutableLiveData<List<DetailedTrainingExercise>>()
    val runningExercises: LiveData<List<DetailedTrainingExercise>> = _runningExercises
    
    private val _plannedExercises = MutableLiveData<List<DetailedTrainingExercise>>()
    val plannedExercises: LiveData<List<DetailedTrainingExercise>> = _plannedExercises
    
    private val _finishedExercises = MutableLiveData<List<DetailedTrainingExercise>>()
    val finishedExercises: LiveData<List<DetailedTrainingExercise>> = _finishedExercises
    
    private val _trainingFinishedEvent = MutableLiveData<Event<Unit>>()
    val trainingFinishedEvent: LiveData<Event<Unit>> = _trainingFinishedEvent
    
    fun start(trainingId: Long) = viewModelScope.launch {
        _training.value = trainingRepository.loadTraining(trainingId)
        val exercises = trainingExerciseRepository.loadDetailedTrainingExerciseList(trainingId)
        
        _runningExercises.value = exercises.filter { it.state == TrainingExercise.RUNNING }
        _plannedExercises.value = exercises.filter { it.state == TrainingExercise.PLANNED }
        _finishedExercises.value = exercises.filter { it.state == TrainingExercise.FINISHED }
    }
    
    fun deleteExercise(exercise: DetailedTrainingExercise) = viewModelScope.launch {
        trainingExerciseRepository.deleteExercise(exercise)
        exerciseRepository.decreaseExecutionsCnt(exercise.exercise)
    }
    
    fun finishTraining(duration: Int) = viewModelScope.launch {
        training.value?.let {
            it.duration = duration
            trainingRepository.updateTraining(it)
            trainingExerciseRepository.finishTrainingExercises(it._id)
            _trainingFinishedEvent.value = Event(Unit)
        }
    }
    
    fun finishExercise(exercise: DetailedTrainingExercise) = viewModelScope.launch {
        trainingExerciseRepository.updateState(exercise._id, TrainingExercise.FINISHED)
        exerciseRepository.increaseExecutionsCnt(exercise.exercise)
    }
    
    fun updateIndexNumbers() = CoroutineScope(Dispatchers.IO).launch {
        plannedExercises.value?.let { list ->
            list.forEachIndexed { index, exercise -> exercise.indexNumber = index + 1 }
            trainingExerciseRepository.updateIndexNumbers(list)
        }
    }
}
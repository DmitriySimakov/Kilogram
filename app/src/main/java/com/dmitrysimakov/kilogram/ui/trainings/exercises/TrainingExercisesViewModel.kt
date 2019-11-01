package com.dmitrysimakov.kilogram.ui.trainings.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch

class TrainingExercisesViewModel(
        private val trainingRepository: TrainingRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val _trainingId = MutableLiveData<Long>()
    
    val training = Transformations.switchMap(_trainingId) {
        trainingRepository.loadTraining(it)
    }
    
    private val exercises = Transformations.switchMap(_trainingId) {
        trainingExerciseRepository.loadDetailedTrainingExerciseList(it)
    }
    
    val runningExercises = Transformations.map(exercises) { exercise ->
        exercise.filter { it.state == TrainingExercise.RUNNING }
    }
    
    val plannedExercises = Transformations.map(exercises) { exercise ->
        exercise.filter { it.state == TrainingExercise.PLANNED }
    }
    
    val finishedExercises = Transformations.map(exercises) { exercise ->
        exercise.filter { it.state == TrainingExercise.FINISHED }
    }
    
    fun setTraining(id: Long) {
        _trainingId.setNewValue(id)
    }

    fun deleteExercise(exercise: DetailedTrainingExercise) { viewModelScope.launch {
        trainingExerciseRepository.deleteExercise(exercise)
        exerciseRepository.decreaseExecutionsCnt(exercise.exercise)
    }}
    
    fun finishTraining(duration: Int) { viewModelScope.launch {
        training.value?.let {
            it.duration = duration
            trainingRepository.updateTraining(it)
            trainingExerciseRepository.finishTrainingExercises(it._id)
        }
    }}
    
    fun finishExercise(exercise: DetailedTrainingExercise) { viewModelScope.launch {
        trainingExerciseRepository.updateState(exercise._id, TrainingExercise.FINISHED)
        exerciseRepository.increaseExecutionsCnt(exercise.exercise)
    }}
    
    fun updateIndexNumbers() { viewModelScope.launch {
        plannedExercises.value?.let { list ->
            list.forEachIndexed { index, exercise -> exercise.indexNumber = index + 1 }
            trainingExerciseRepository.updateIndexNumbers(list)
        }
    }}
}
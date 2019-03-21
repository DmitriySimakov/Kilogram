package com.dmitrysimakov.kilogram.ui.trainings.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import javax.inject.Inject

class TrainingExercisesViewModel @Inject constructor(
        private val trainingRepository: TrainingRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository
) : ViewModel() {

    private val _trainingId = MutableLiveData<Long>()
    
    val training = Transformations.switchMap(_trainingId) {
        trainingRepository.loadTraining(it)
    }
    
    private val exercises = Transformations.switchMap(_trainingId) {
        trainingExerciseRepository.loadTrainingExerciseRs(it)
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

    fun deleteExercise(exercise: TrainingExerciseR) {
        trainingExerciseRepository.deleteExercise(exercise)
    }
    
    fun finishTraining() {
        training.value?.let {
            it.duration = 10 // TODO
            trainingRepository.updateTraining(it)
            trainingExerciseRepository.finishTrainingExercises(it._id)
        }
    }
    
    fun finishExercise(exercise: TrainingExerciseR) {
        trainingExerciseRepository.updateState(exercise._id, TrainingExercise.FINISHED)
    }
}
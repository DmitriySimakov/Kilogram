package com.dmitrysimakov.kilogram.ui.trainings.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.setNewValue

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

    fun deleteExercise(exercise: DetailedTrainingExercise) {
        trainingExerciseRepository.deleteExercise(exercise)
        exerciseRepository.decreaseExecutionsCnt(exercise.exercise)
    }
    
    fun finishTraining(duration: Int) {
        training.value?.let {
            it.duration = duration
            trainingRepository.updateTraining(it)
            trainingExerciseRepository.finishTrainingExercises(it._id)
        }
    }
    
    fun finishExercise(exercise: DetailedTrainingExercise) {
        trainingExerciseRepository.updateState(exercise._id, TrainingExercise.FINISHED)
        exerciseRepository.increaseExecutionsCnt(exercise.exercise)
    }
    
    fun updateNums() {
        plannedExercises.value?.let { list ->
            list.forEachIndexed { index, exercise -> exercise.indexNumber = index + 1 }
            trainingExerciseRepository.updateIndexNumbers(list)
        }
    }
}
package com.dmitrysimakov.kilogram.ui.common.add_exercise

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import com.dmitrysimakov.kilogram.util.setNewValue
import javax.inject.Inject

class AddExerciseViewModel @Inject constructor(
        private val exerciseRepository: ExerciseRepository,
        private val trainingRepository: TrainingRepository,
        private val programRepository: ProgramRepository
) : ViewModel() {
    
    private val _exerciseId = MutableLiveData<Long>()
    
    val exercise = Transformations.switchMap(_exerciseId) { id ->
        when (id) {
            null -> AbsentLiveData.create()
            else -> exerciseRepository.loadExercise(id)
        }
    }
    
    fun setExercise(id: Long) {
        _exerciseId.setNewValue(id)
    }
    
    fun addExerciseToTraining(trainingId: Long) {
        exercise.value?.let { trainingRepository.addExercise(it, trainingId) }
    }
    
    fun addExerciseToProgramDay(programDayId: Long) {
        exercise.value?.let { programRepository.addExerciseToProgramDay(it, programDayId) }
    }
    
    fun updateMeasures() {
        exercise.value?.let { exerciseRepository.updateExercise(it) }
    }
}
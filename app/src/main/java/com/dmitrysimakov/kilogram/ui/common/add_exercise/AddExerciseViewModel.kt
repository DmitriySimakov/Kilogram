package com.dmitrysimakov.kilogram.ui.common.add_exercise

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import javax.inject.Inject

class AddExerciseViewModel @Inject constructor(
        private val exerciseRepository: ExerciseRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val programDayExerciseRepository: ProgramDayExerciseRepository
) : ViewModel() {
    
    private val _exerciseId = MutableLiveData<Long>()
    
    val exercise = Transformations.switchMap(_exerciseId) {
        exerciseRepository.loadExercise(it)
    }
    
    fun setExercise(id: Long) {
        _exerciseId.setNewValue(id)
    }
    
    fun addExerciseToTraining(trainingId: Long, num: Int) {
        exercise.value?.run { trainingExerciseRepository.addExercise(
                TrainingExercise(0, trainingId, _id, num, null, measures)) }
    }
    
    fun addExerciseToProgramDay(programDayId: Long, num: Int) {
        exercise.value?.run { programDayExerciseRepository.addExerciseToProgramDay(
                ProgramDayExercise(0, programDayId, _id, num, null, measures)) }
    }
    
    fun updateMeasures() {
        exercise.value?.let { exerciseRepository.updateExercise(it) }
    }
}
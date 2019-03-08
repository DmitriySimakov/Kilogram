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
    
    val restTime = MutableLiveData(3*60)
    
    val strategy = MutableLiveData<String>()
    
    fun addExerciseToTraining(trainingId: Long, num: Int) {
        exercise.value?.let { trainingExerciseRepository.addExercise(
                TrainingExercise(0, trainingId, it._id, num, restTime.value?: 3*60, strategy.value, it.measures)) }
    }
    
    fun addExerciseToProgramDay(programDayId: Long, num: Int) {
        exercise.value?.let { programDayExerciseRepository.addExerciseToProgramDay(
                ProgramDayExercise(0, programDayId, it._id, num, restTime.value?: 3*60, strategy.value, it.measures)) }
    }
    
    fun updateMeasures() {
        exercise.value?.let { exerciseRepository.updateExercise(it) }
    }
}
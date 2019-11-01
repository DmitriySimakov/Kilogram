package com.dmitrysimakov.kilogram.ui.common.add_exercise

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch

class AddExerciseViewModel (
        private val exerciseRepository: ExerciseRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val programDayExerciseRepository: ProgramDayExerciseRepository
) : ViewModel() {
    
    private val _exerciseName = MutableLiveData<String>()
    fun setExercise(name: String) {
        _exerciseName.setNewValue(name)
    }
    
    val exercise = Transformations.switchMap(_exerciseName) {
        exerciseRepository.loadExercise(it)
    }
    
    private val _restTime = MutableLiveData(3*60) //TODO
    val restTime: LiveData<Int>
        get() = _restTime
    
    val strategy = MutableLiveData<String>()
    
    fun addExerciseToTraining(trainingId: Long, num: Int, rest: Int) { viewModelScope.launch { //TODO
        exercise.value?.let { trainingExerciseRepository.addExercise(
                TrainingExercise(0, trainingId, it.name, num, rest, strategy.value, TrainingExercise.PLANNED, it.measures)) }
    }}
    
    fun addExerciseToProgramDay(programDayId: Long, num: Int, rest: Int) { viewModelScope.launch { //TODO
        exercise.value?.let { programDayExerciseRepository.addExerciseToProgramDay(
                ProgramDayExercise(0, programDayId, it.name, num, rest, strategy.value, it.measures)) }
    }}
    
    fun updateMeasures() { viewModelScope.launch {
        exercise.value?.let { exerciseRepository.updateExercise(it) }
    }}
}
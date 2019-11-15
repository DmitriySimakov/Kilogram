package com.dmitrysimakov.kilogram.ui.common.add_exercise

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.Exercise
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.util.Event
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddExerciseViewModel (
        private val exerciseRepository: ExerciseRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val programDayExerciseRepository: ProgramDayExerciseRepository
) : ViewModel() {
    
    val restTime: LiveData<Int> = MutableLiveData(3*60)
    
    val strategy = MutableLiveData<String>()
    
    private val _exercise = MutableLiveData<Exercise>()
    val exercise: LiveData<Exercise> = _exercise
    
    private val _exerciseAddedEvent = MutableLiveData<Event<Unit>>()
    val exerciseAddedEvent: LiveData<Event<Unit>> = _exerciseAddedEvent
    
    fun start(exerciseName: String) = viewModelScope.launch {
        _exercise.value = exerciseRepository.loadExercise(exerciseName)
    }
    
    fun addExerciseToTraining(trainingId: Long, num: Int, rest: Int) = viewModelScope.launch {
        exercise.value?.let {
            trainingExerciseRepository.addExercise(
                TrainingExercise(
                    0,
                    trainingId,
                    it.name,
                    num,
                    rest,
                    strategy.value,
                    TrainingExercise.PLANNED,
                    it.measures
                )
            )
            updateMeasures()
            _exerciseAddedEvent.value = Event(Unit)
        }
    }
    
    fun addExerciseToProgramDay(programDayId: Long, num: Int, rest: Int) = viewModelScope.launch {
        exercise.value?.let {
            programDayExerciseRepository.addExerciseToProgramDay(
                ProgramDayExercise(0, programDayId, it.name, num, rest, strategy.value, it.measures)
            )
            updateMeasures()
            _exerciseAddedEvent.value = Event(Unit)
        }
    }
    
    private suspend fun updateMeasures() {
        exercise.value?.let { exerciseRepository.updateExercise(it) }
    }
}
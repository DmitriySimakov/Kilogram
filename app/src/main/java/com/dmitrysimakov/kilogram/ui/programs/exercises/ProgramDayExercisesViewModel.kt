package com.dmitrysimakov.kilogram.ui.programs.exercises

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class ProgramDayExercisesViewModel (private val repository: ProgramDayExerciseRepository) : ViewModel() {
    
    private val _exercises = MutableLiveData<List<ProgramDayExercise>>()
    val exercises: LiveData<List<ProgramDayExercise>> = _exercises
    
    fun start(programDayId: Long) = viewModelScope.launch {
        _exercises.value = repository.loadProgramDayExerciseList(programDayId)
    }

    fun deleteExercise(exercise: ProgramDayExercise) = viewModelScope.launch {
        repository.deleteExerciseFromProgramDay(exercise)
    }
    
    fun updateIndexNumbers() { CoroutineScope(Dispatchers.IO).launch {
        exercises.value?.let { list ->
            list.forEachIndexed { index, exercise -> exercise.indexNumber = index + 1 }
            repository.update(list)
        }
    }}
}
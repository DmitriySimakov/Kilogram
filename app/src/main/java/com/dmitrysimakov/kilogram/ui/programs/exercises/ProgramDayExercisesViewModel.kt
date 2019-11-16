package com.dmitrysimakov.kilogram.ui.programs.exercises

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgramDayExercisesViewModel (private val repository: ProgramDayExerciseRepository) : ViewModel() {
    
    private val _exercises = MutableLiveData<List<ProgramDayExercise>>()
    val exercises: LiveData<List<ProgramDayExercise>> = _exercises
    
    fun start(programDayId: Long) = viewModelScope.launch {
        _exercises.value = repository.programDayExercises(programDayId)
    }

    fun deleteExercise(id: Long) = viewModelScope.launch {
        repository.delete(id)
    }
    
    fun updateIndexNumbers() { CoroutineScope(Dispatchers.IO).launch {
        exercises.value?.let { list ->
            list.forEachIndexed { index, exercise -> exercise.indexNumber = index + 1 }
            repository.update(list)
        }
    }}
}
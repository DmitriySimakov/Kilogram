package com.dmitrysimakov.kilogram.ui.home.programs.exercises

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgramDayExercisesViewModel(private val repository: ProgramDayExerciseRepository) : ViewModel() {
    
    val programDayId = MutableLiveData<String>()
    
    val exercises = programDayId.switchMap { repository.programDayExercisesFlow(it).asLiveData() }
    
    fun deleteExercise(id: String) = viewModelScope.launch {
        repository.delete(id)
    }
    
    fun updateIndexNumbers() { CoroutineScope(Dispatchers.IO).launch {
        val exercises = exercises.value ?: return@launch
        val newList = exercises.mapIndexed { index, exercise ->
            exercise.copy(indexNumber = index + 1)
        }
        repository.update(newList)
    }}
}
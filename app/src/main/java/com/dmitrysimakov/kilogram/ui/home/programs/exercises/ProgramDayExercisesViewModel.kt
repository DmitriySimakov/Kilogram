package com.dmitrysimakov.kilogram.ui.home.programs.exercises

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgramDayExercisesViewModel(private val repository: ProgramDayExerciseRepository) : ViewModel() {
    
    private val _programDayId = MutableLiveData<Long>()
    val exercises = _programDayId.switchMap { repository.programDayExercisesFlow(it).asLiveData() }
    
    fun setProgramDayId(id: Long) = _programDayId.setNewValue(id)

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
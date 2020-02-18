package com.dmitrysimakov.kilogram.ui.home.programs.exercises

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.live_data.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgramDayExercisesViewModel(
        private val programDayRepo: ProgramDayRepository,
        private val programDayExerciseRepo: ProgramDayExerciseRepository
) : ViewModel() {
    
    val programDayId = MutableLiveData<String>()
    
    val exercises = programDayId.switchMap {
        programDayExerciseRepo.programDayExercisesFlow(it).asLiveData()
    }
    
    val programDayDeletedEvent = MutableLiveData<Event<Unit>>()
    
    fun deleteExercise(id: String) = viewModelScope.launch {
        programDayExerciseRepo.delete(id)
    }
    
    fun deleteProgramDay() {
        val id = programDayId.value ?: return
        CoroutineScope(Dispatchers.IO).launch { programDayRepo.delete(id) }
        programDayDeletedEvent.value = Event(Unit)
    }
    
    fun updateIndexNumbers() { CoroutineScope(Dispatchers.IO).launch {
        val exercises = exercises.value ?: return@launch
        val newList = exercises.mapIndexed { index, exercise ->
            exercise.copy(indexNumber = index + 1)
        }
        programDayExerciseRepo.update(newList)
    }}
}
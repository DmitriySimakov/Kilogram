package com.dmitrysimakov.kilogram.ui.home.programs.program_days

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgramDaysViewModel (private val repository: ProgramDayRepository) : ViewModel() {
    
    val programDayId = MutableLiveData<Long>()
    
    val programDays = programDayId.switchMap { repository.programDaysFlow(it).asLiveData() }
    
    fun deleteProgramDay(id: Long) = viewModelScope.launch {
        repository.delete(id)
    }
    
    fun updateIndexNumbers() { CoroutineScope(Dispatchers.IO).launch {
        programDays.value?.let { list ->
            list.forEachIndexed { index, exercise -> exercise.indexNumber = index + 1 }
            repository.update(list)
        }
    }}
}
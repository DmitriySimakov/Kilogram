package com.dmitrysimakov.kilogram.ui.common.choose_program_day

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChooseProgramDayViewModel (private val repository: ProgramDayRepository) : ViewModel() {
    
    private val _programDayId = MutableLiveData<Long>()
    
    val programDays = _programDayId.switchMap { repository.programDaysFlow(it).asLiveData() }
    
    fun setProgramId(id: Long) = _programDayId.setNewValue(id)
    
    fun deleteTrainingDay(id: Long) = viewModelScope.launch {
        repository.delete(id)
    }
    
    fun updateIndexNumbers() { CoroutineScope(Dispatchers.IO).launch {
        programDays.value?.let { list ->
            list.forEachIndexed { index, exercise -> exercise.indexNumber = index + 1 }
            repository.update(list)
        }
    }}
}
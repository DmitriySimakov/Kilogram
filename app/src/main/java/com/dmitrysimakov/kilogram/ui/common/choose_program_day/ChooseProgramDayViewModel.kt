package com.dmitrysimakov.kilogram.ui.common.choose_program_day

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChooseProgramDayViewModel (private val repository: ProgramDayRepository) : ViewModel() {
    
    private val _programDays = MutableLiveData<List<ProgramDay>>()
    val programDays: LiveData<List<ProgramDay>> = _programDays
    
    fun start(programId: Long) = viewModelScope.launch {
        _programDays.value = repository.loadProgramDays(programId)
    }
    
    fun deleteTrainingDay(id: Long) = viewModelScope.launch {
        repository.deleteProgramDay(id)
    }
    
    fun updateIndexNumbers() { CoroutineScope(Dispatchers.IO).launch {
        programDays.value?.let { list ->
            list.forEachIndexed { index, exercise -> exercise.indexNumber = index + 1 }
            repository.update(list)
        }
    }}
}
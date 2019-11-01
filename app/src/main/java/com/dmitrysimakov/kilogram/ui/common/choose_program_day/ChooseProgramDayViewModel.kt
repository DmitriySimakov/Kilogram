package com.dmitrysimakov.kilogram.ui.common.choose_program_day

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch
import timber.log.Timber

class ChooseProgramDayViewModel (private val repository: ProgramDayRepository) : ViewModel() {
    
    private val _programId = MutableLiveData<Long>()
    fun setProgram(programId: Long) {
        _programId.setNewValue(programId)
    }
    
    val programDays = _programId.switchMap {
        Timber.d("programDays load")
        repository.loadTrainingDays(it)
    }

    fun deleteTrainingDay(id: Long) { viewModelScope.launch {
        repository.deleteProgramDay(id)
    } }
    
    fun updateIndexNumbers() { viewModelScope.launch {
        programDays.value?.let { list ->
            list.forEachIndexed { index, exercise -> exercise.indexNumber = index + 1 }
            repository.update(list)
        }
    }}
}
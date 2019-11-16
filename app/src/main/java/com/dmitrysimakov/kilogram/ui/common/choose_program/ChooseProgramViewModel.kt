package com.dmitrysimakov.kilogram.ui.common.choose_program

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import kotlinx.coroutines.launch

class ChooseProgramViewModel (private val repository: ProgramRepository) : ViewModel() {

    val programList = liveData { emit(repository.programs()) }
    
    fun deleteProgram(id: Long) = viewModelScope.launch {
        repository.delete(id)
    }
}
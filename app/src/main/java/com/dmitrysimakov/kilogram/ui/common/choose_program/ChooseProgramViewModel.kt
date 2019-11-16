package com.dmitrysimakov.kilogram.ui.common.choose_program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import kotlinx.coroutines.launch

class ChooseProgramViewModel (private val repository: ProgramRepository) : ViewModel() {

    val programs = repository.programsFlow().asLiveData()
    
    fun deleteProgram(id: Long) = viewModelScope.launch {
        repository.delete(id)
    }
}
package com.dmitrysimakov.kilogram.ui.common.choose_program

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.Program
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChooseProgramViewModel (private val repository: ProgramRepository) : ViewModel() {

    val programList = liveData { emit(repository.loadProgramList()) }
    
    fun deleteProgram(id: Long) = viewModelScope.launch {
        repository.deleteProgram(id)
    }
}
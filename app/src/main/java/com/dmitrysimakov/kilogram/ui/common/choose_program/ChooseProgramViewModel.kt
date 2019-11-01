package com.dmitrysimakov.kilogram.ui.common.choose_program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import kotlinx.coroutines.launch

class ChooseProgramViewModel (private val repository: ProgramRepository) : ViewModel() {

    val programList = repository.loadProgramList()

    fun deleteProgram(id: Long) { viewModelScope.launch {
        repository.deleteProgram(id)
    }}
}
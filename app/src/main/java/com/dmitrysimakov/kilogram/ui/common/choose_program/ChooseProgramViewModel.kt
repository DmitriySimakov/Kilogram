package com.dmitrysimakov.kilogram.ui.common.choose_program

import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChooseProgramViewModel (private val repository: ProgramRepository) : ViewModel() {

    val programList = repository.loadProgramList()

    fun deleteProgram(id: Long) { CoroutineScope(Dispatchers.IO).launch {
        repository.deleteProgram(id)
    }}
}
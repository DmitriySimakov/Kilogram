package com.dmitrysimakov.kilogram.ui.common.choose_program

import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository

class ChooseProgramViewModel (private val repository: ProgramRepository) : ViewModel() {

    val programList = repository.loadProgramList()

    fun deleteProgram(id: Long) {
        repository.deleteProgram(id)
    }
}
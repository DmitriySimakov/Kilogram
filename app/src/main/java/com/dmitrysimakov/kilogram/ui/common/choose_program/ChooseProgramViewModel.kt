package com.dmitrysimakov.kilogram.ui.common.choose_program

import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.Program
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import javax.inject.Inject


class ChooseProgramViewModel @Inject constructor(private val repository: ProgramRepository) : ViewModel() {

    val programList = repository.loadProgramList()

    fun deleteProgram(program: Program) {
        repository.deleteProgram(program)
    }
}
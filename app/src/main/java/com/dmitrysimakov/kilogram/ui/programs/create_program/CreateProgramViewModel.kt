package com.dmitrysimakov.kilogram.ui.programs.create_program

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.local.entity.Program
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import javax.inject.Inject

class CreateProgramViewModel @Inject constructor(
        private val repository: ProgramRepository
) : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")

    fun createProgram(callback: ((Long) -> Unit)) {
        repository.insertProgram(Program(0, name.value!!, description.value!!), callback)
    }
}
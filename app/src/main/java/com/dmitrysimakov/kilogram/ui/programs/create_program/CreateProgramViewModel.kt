package com.dmitrysimakov.kilogram.ui.programs.create_program

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.local.entity.Program
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import kotlinx.coroutines.launch

class CreateProgramViewModel (private val repository: ProgramRepository) : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")

    fun createProgram(callback: ((Long) -> Unit)) { viewModelScope.launch {
        repository.insertProgram(Program(0, name.value!!, description.value!!), callback)
    }}
}
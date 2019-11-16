package com.dmitrysimakov.kilogram.ui.programs.create_program

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.local.entity.Program
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository

class CreateProgramViewModel (private val repository: ProgramRepository) : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    
    suspend fun createProgram() = repository.insert(
            Program(0, name.value!!, description.value!!))
}
package com.dmitrysimakov.kilogram.ui.home.programs.create_program

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.local.entity.Program
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import com.dmitrysimakov.kilogram.util.live_data.Event
import kotlinx.coroutines.launch

class CreateProgramViewModel (private val repository: ProgramRepository) : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    
    val programCreatedEvent = MutableLiveData<Event<Long>>()
    
    fun createProgram() = viewModelScope.launch {
        val id = repository.insert(Program(0, name.value!!, description.value!!))
        programCreatedEvent.value = Event(id)
    }
}
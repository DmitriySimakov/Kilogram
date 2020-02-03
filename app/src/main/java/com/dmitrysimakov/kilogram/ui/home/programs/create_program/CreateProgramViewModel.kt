package com.dmitrysimakov.kilogram.ui.home.programs.create_program

import androidx.lifecycle.LiveData
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
    
    private val _programCreatedEvent = MutableLiveData<Event<Long>>()
    val programCreatedEvent: LiveData<Event<Long>> = _programCreatedEvent
    
    fun createProgram() = viewModelScope.launch {
        val id = repository.insert(Program(0, name.value!!, description.value!!))
        _programCreatedEvent.value = Event(id)
    }
}
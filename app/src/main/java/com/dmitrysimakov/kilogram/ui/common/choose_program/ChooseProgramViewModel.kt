package com.dmitrysimakov.kilogram.ui.common.choose_program

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.model.Program
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import com.dmitrysimakov.kilogram.util.live_data.Event
import kotlinx.coroutines.launch

class ChooseProgramViewModel (private val repository: ProgramRepository) : ViewModel() {

    val programs = repository.programsFlow().asLiveData()
    val programPublishedEvent = MutableLiveData<Event<Unit>>()
    
    fun publishProgram(program: Program) = viewModelScope.launch {
        repository.publishProgram(program)
    
        programPublishedEvent.value = Event(Unit)
    }
}
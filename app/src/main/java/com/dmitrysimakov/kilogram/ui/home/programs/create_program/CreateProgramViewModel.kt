package com.dmitrysimakov.kilogram.ui.home.programs.create_program

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.model.Program
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import com.dmitrysimakov.kilogram.util.live_data.Event
import kotlinx.coroutines.launch

class CreateProgramViewModel (private val repository: ProgramRepository) : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    val author = MutableLiveData<User?>()
    
    val programCreatedEvent = MutableLiveData<Event<String>>()
    
    fun createProgram() = viewModelScope.launch {
        val author = author.value
        val program = Program(name.value!!, description.value!!, author?.id, author?.name)
        repository.insert(program)
        programCreatedEvent.value = Event(program.id)
    }
}
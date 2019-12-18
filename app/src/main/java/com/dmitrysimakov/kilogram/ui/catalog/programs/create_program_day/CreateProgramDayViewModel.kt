package com.dmitrysimakov.kilogram.ui.catalog.programs.create_program_day

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.Event
import kotlinx.coroutines.launch

class CreateProgramDayViewModel(private val programDayRepo: ProgramDayRepository) : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    
    private val _programDayCreatedEvent = MutableLiveData<Event<Long>>()
    val programDayCreatedEvent: LiveData<Event<Long>> = _programDayCreatedEvent
    
    fun createProgramDay(programId: Long, num: Int) = viewModelScope.launch {
        val programDay = ProgramDay(0, programId, num, name.value!!, description.value!!)
        val programDayId = programDayRepo.insert(programDay)
        _programDayCreatedEvent.value = Event(programDayId)
    }
}
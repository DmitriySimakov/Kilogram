package com.dmitrysimakov.kilogram.ui.home.programs.create_program_day

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.live_data.Event
import kotlinx.coroutines.launch

class CreateProgramDayViewModel(private val programDayRepo: ProgramDayRepository) : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    
    val programDayCreatedEvent = MutableLiveData<Event<Long>>()
    
    fun createProgramDay(programId: Long, num: Int) = viewModelScope.launch {
        val programDay = ProgramDay(0, programId, num, name.value!!, description.value!!)
        val programDayId = programDayRepo.insert(programDay)
        programDayCreatedEvent.value = Event(programDayId)
    }
}
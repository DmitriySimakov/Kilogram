package com.dmitrysimakov.kilogram.ui.feed.program_days

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import com.dmitrysimakov.kilogram.util.live_data.Event
import kotlinx.coroutines.launch

class PublicProgramDaysViewModel (
        private val programRepo: ProgramRepository,
        private val programDayRepo: ProgramDayRepository
) : ViewModel() {
    
    val programId = MutableLiveData<String>()
    
    val program = programId.switchMap { programId -> liveData {
        emit(programRepo.publicProgram(programId))
    }}
    
    val programDays = programId.switchMap { programId -> liveData {
        emit(programDayRepo.publicProgramDays(programId))
    }}
    
    val programAddedToMyProgramsEvent = MutableLiveData<Event<Unit>>()
    
    fun addToMyPrograms() { viewModelScope.launch {
        programRepo.addToMyPrograms(program.value!!)
        programAddedToMyProgramsEvent.value = Event(Unit)
    }}
}
package com.dmitrysimakov.kilogram.ui.feed.program

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository

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
}
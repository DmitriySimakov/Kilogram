package com.dmitrysimakov.kilogram.ui.feed.program_day_exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.model.ProgramDay
import com.dmitrysimakov.kilogram.data.model.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch

class PublicProgramDayExercisesViewModel(
        private val programDayRepo: ProgramDayRepository,
        private val programDayExerciseRepo: ProgramDayExerciseRepository
) : ViewModel() {
    
    val programDay = MutableLiveData<ProgramDay>()
    
    val exercises = MutableLiveData<List<ProgramDayExercise>>()
    
    fun start(programId: String, programDayId: String) { viewModelScope.launch {
        programDay.setNewValue(programDayRepo.publicProgramDay(programId, programDayId))
        exercises.setNewValue(programDayExerciseRepo.publicProgramDayExercises(programId, programDayId))
    }}
}
package com.dmitrysimakov.kilogram.ui.catalogs.programs.create_program_day

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.dao.ExerciseTargetDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayTarget
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramDayTargetsRepository
import com.dmitrysimakov.kilogram.util.Event
import kotlinx.coroutines.launch

class CreateProgramDayViewModel(
        private val exerciseTargetDao: ExerciseTargetDao,
        private val programDayRepo: ProgramDayRepository,
        private val programDayTargetsRepo: ProgramDayTargetsRepository
) : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    
    val muscleList = liveData { emit(exerciseTargetDao.params()) }
    
    private val _programDayCreatedEvent = MutableLiveData<Event<Long>>()
    val programDayCreatedEvent: LiveData<Event<Long>> = _programDayCreatedEvent
    
    fun createProgramDay(programId: Long, num: Int) = viewModelScope.launch {
        val programDayId = programDayRepo.insert(
                ProgramDay(0, programId, num, name.value!!, description.value!!)
        )
        saveMuscles(programDayId)
        _programDayCreatedEvent.value = Event(programDayId)
    }
    
    private suspend fun saveMuscles(programDayId: Long) {
        val list = mutableListOf<ProgramDayTarget>()
        for (muscle in muscleList.value!!) {
            if (muscle.is_active) list.add(ProgramDayTarget(programDayId, muscle.name))
        }
        programDayTargetsRepo.insert(list)
    }
}
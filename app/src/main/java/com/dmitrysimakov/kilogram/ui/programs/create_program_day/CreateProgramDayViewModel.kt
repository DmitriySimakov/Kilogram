package com.dmitrysimakov.kilogram.ui.programs.create_program_day

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.dao.MuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayMuscle
import com.dmitrysimakov.kilogram.data.repository.ProgramDayMuscleRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.Event
import kotlinx.coroutines.launch

class CreateProgramDayViewModel(
        private val muscleDao: MuscleDao,
        private val programDayRepo: ProgramDayRepository,
        private val programDayMuscleRepo: ProgramDayMuscleRepository
) : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    
    val muscleList = liveData { emit(muscleDao.params()) }
    
    private val _programDayCreatedEvent = MutableLiveData<Event<Long>>()
    val programDayCreatedEvent: LiveData<Event<Long>> = _programDayCreatedEvent
    
    private var programId: Long? = null
    
    fun start(programId: Long) {
        this.programId = programId
    }
    
    fun createProgramDay(num: Int) = viewModelScope.launch {
        val programDayId = programDayRepo.insert(
                ProgramDay(0, programId!!, num, name.value!!, description.value!!)
        )
        saveMuscles(programDayId)
        _programDayCreatedEvent.value = Event(programDayId)
    }
    
    private suspend fun saveMuscles(programDayId: Long) {
        val list = mutableListOf<ProgramDayMuscle>()
        for (muscle in muscleList.value!!) {
            if (muscle.is_active) list.add(ProgramDayMuscle(programDayId, muscle.name))
        }
        programDayMuscleRepo.insert(list)
    }
}
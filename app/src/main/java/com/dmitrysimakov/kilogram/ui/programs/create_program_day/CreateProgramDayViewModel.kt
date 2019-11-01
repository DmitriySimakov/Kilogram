package com.dmitrysimakov.kilogram.ui.programs.create_program_day

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayMuscle
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramDayMuscleRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch

class CreateProgramDayViewModel(
        private val programDayRepo: ProgramDayRepository,
        private val exerciseRepo: ExerciseRepository,
        private val programDayMuscleRepo: ProgramDayMuscleRepository
) : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    
    private val _programId = MutableLiveData<Long>()
    
    fun setProgram(id: Long) {
        _programId.setNewValue(id)
    }
    
    fun createProgramDay(num: Int, callback: ((Long) -> Unit)) { viewModelScope.launch {
        _programId.value?.let {
            programDayRepo.insertProgramDay(ProgramDay(0, it, num, name.value!!, description.value!!), callback)
        }
    }}
    
    val muscleList = exerciseRepo.loadMuscleParams()
    
    fun saveMuscles(trainingId: Long) { viewModelScope.launch {
        val list = mutableListOf<ProgramDayMuscle>()
        for (muscle in muscleList.value!!) {
            if (muscle.is_active) list.add(ProgramDayMuscle(trainingId, muscle.name))
        }
        programDayMuscleRepo.insert(list)
    }}
}
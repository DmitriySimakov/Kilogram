package com.dmitrysimakov.kilogram.ui.programs.create_program_day

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.local.dao.MuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayMuscle
import com.dmitrysimakov.kilogram.data.repository.ProgramDayMuscleRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateProgramDayViewModel(
        muscleDao: MuscleDao,
        private val programDayRepo: ProgramDayRepository,
        private val programDayMuscleRepo: ProgramDayMuscleRepository
) : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    
    private val _programId = MutableLiveData<Long>()
    
    fun setProgram(id: Long) {
        _programId.setNewValue(id)
    }
    
    suspend fun createProgramDay(num: Int) = _programId.value?.let {
        programDayRepo.insertProgramDay(ProgramDay(0, it, num, name.value!!, description.value!!))
    }
    
    val muscleList = muscleDao.getParamList()
    
    fun saveMuscles(trainingId: Long) { CoroutineScope(Dispatchers.IO).launch {
        val list = mutableListOf<ProgramDayMuscle>()
        for (muscle in muscleList.value!!) {
            if (muscle.is_active) list.add(ProgramDayMuscle(trainingId, muscle.name))
        }
        programDayMuscleRepo.insert(list)
    }}
}
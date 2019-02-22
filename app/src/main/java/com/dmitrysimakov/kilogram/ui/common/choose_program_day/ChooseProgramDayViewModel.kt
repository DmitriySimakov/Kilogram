package com.dmitrysimakov.kilogram.ui.common.choose_program_day

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import com.dmitrysimakov.kilogram.util.setNewValue
import javax.inject.Inject


class ChooseProgramDayViewModel @Inject constructor(private val repository: ProgramRepository) : ViewModel() {
    
    private val _programId = MutableLiveData<Long>()
    
    val trainingDays = Transformations.switchMap(_programId) { id ->
        when(id) {
            null -> AbsentLiveData.create()
            else -> repository.loadTrainingDays(id)
        }
    }
    
    fun setProgram(programId: Long) {
        _programId.setNewValue(programId)
    }

    fun deleteTrainingDay(day: ProgramDay) {
        repository.deleteTrainingDay(day)
    }
}
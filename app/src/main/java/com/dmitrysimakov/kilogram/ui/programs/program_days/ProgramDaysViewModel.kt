package com.dmitrysimakov.kilogram.ui.programs.program_days

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import javax.inject.Inject


class ProgramDaysViewModel @Inject constructor(private val repository: ProgramRepository) : ViewModel() {
    
    private val _programId = MutableLiveData<Long>()
    
    val trainingDays = Transformations.switchMap(_programId) { id ->
        when(id) {
            null -> AbsentLiveData.create()
            else -> repository.loadTrainingDays(id)
        }
    }
    
    fun setParams(programId: Long) {
        if (_programId.value != programId) {
            _programId.value = programId
        }
    }

    fun deleteTrainingDay(day: ProgramDay) {
        repository.deleteTrainingDay(day)
    }
}
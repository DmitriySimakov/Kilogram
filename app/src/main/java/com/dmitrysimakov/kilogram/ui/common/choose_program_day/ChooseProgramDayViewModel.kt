package com.dmitrysimakov.kilogram.ui.common.choose_program_day

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import timber.log.Timber

class ChooseProgramDayViewModel (private val repository: ProgramDayRepository) : ViewModel() {
    
    private val _programId = MutableLiveData<Long>()
    
    val programDays = Transformations.switchMap(_programId) {
        Timber.d("programDays load")
        repository.loadTrainingDays(it)
    }
    
    fun setProgram(programId: Long) {
        _programId.setNewValue(programId)
    }

    fun deleteTrainingDay(day: ProgramDay) {
        repository.deleteProgramDay(day)
    }
    
    fun updateNums() {
        programDays.value?.let { list ->
            list.forEachIndexed { index, exercise -> exercise.num = index + 1 }
            repository.updateNums(list)
        }
    }
}
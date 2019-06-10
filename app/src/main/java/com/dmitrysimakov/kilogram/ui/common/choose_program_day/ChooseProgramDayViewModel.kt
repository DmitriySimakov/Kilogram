package com.dmitrysimakov.kilogram.ui.common.choose_program_day

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import timber.log.Timber
import javax.inject.Inject

class ChooseProgramDayViewModel @Inject constructor(
        private val repository: ProgramDayRepository
) : ViewModel() {
    
    private val _programId = MutableLiveData<Long>()
    
    val trainingDays = Transformations.switchMap(_programId) {
        Timber.d("trainingDays load")
        repository.loadTrainingDays(it)
    }
    
    fun setProgram(programId: Long) {
        _programId.setNewValue(programId)
    }

    fun deleteTrainingDay(day: ProgramDay) {
        repository.deleteProgramDay(day)
    }
    
    fun updateNums(items: List<ProgramDay>) {
        repository.updateNums(items)
    }
}
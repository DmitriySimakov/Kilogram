package com.dmitrysimakov.kilogram.ui.common.choose_program_day

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import javax.inject.Inject


class ChooseProgramDayViewModel @Inject constructor(private val repository: ProgramRepository) : ViewModel() {
    
    private val TAG = this::class.java.simpleName
    
    private val _programId = MutableLiveData<Long>()
    
    val trainingDays = Transformations.switchMap(_programId) {
        Log.d(TAG, "trainingDays load")
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
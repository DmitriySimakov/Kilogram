package com.dmitrysimakov.kilogram.ui.common.choose_program_day

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class ChooseProgramDayViewModel (private val repository: ProgramDayRepository) : ViewModel() {
    
    private val _programId = MutableLiveData<Long>()
    fun setProgram(programId: Long) {
        _programId.setNewValue(programId)
    }
    
    val programDays = _programId.switchMap {
        Timber.d("programDays load")
        repository.loadTrainingDays(it)
    }

    fun deleteTrainingDay(id: Long) { CoroutineScope(Dispatchers.IO).launch {
        repository.deleteProgramDay(id)
    } }
    
    fun updateIndexNumbers() { CoroutineScope(Dispatchers.IO).launch {
        programDays.value?.let { list ->
            list.forEachIndexed { index, exercise -> exercise.indexNumber = index + 1 }
            repository.update(list)
        }
    }}
}
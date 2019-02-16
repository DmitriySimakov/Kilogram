package com.dmitrysimakov.kilogram.ui.programs.create_program_day

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.data.entity.Program
import com.dmitrysimakov.kilogram.data.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.data.entity.Training
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CreateProgramDayViewModel @Inject constructor(
        private val repository: ProgramRepository
) : ViewModel() {

    val name = MutableLiveData<String>()
    
    private val _programId = MutableLiveData<Long>()
    
    fun setParams(programId: Long) {
        if (_programId.value != programId) {
            _programId.value = programId
        }
    }
    
    fun createProgramDay(callback: ItemInsertedListener) {
        if (name.value != null && _programId.value != null) {
            repository.insertProgramDay(ProgramDay(0, name.value!!, 0, _programId.value!!), callback)
        }
    }
}
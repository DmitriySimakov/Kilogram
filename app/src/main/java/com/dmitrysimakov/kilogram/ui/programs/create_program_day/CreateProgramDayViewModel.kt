package com.dmitrysimakov.kilogram.ui.programs.create_program_day

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.data.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import javax.inject.Inject

class CreateProgramDayViewModel @Inject constructor(
        private val repository: ProgramDayRepository
) : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    
    private val _programId = MutableLiveData<Long>()
    
    fun setProgram(id: Long) {
        _programId.setNewValue(id)
    }
    
    fun createProgramDay(num: Int, callback: ItemInsertedListener) {
        _programId.value?.let {
            repository.insertProgramDay(ProgramDay(0, it, num, name.value!!, description.value!!), callback)
        }
    }
}
package com.dmitrysimakov.kilogram.ui.programs.create_program

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.data.entity.Program
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.data.entity.Training
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CreateProgramViewModel @Inject constructor(
        private val repository: ProgramRepository
) : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")

    fun createProgram(callback: ItemInsertedListener) {
        repository.insertProgram(Program(0, name.value!!, description.value!!), callback)
    }
}
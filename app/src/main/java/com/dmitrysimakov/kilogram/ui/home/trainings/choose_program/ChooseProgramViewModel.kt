package com.dmitrysimakov.kilogram.ui.home.trainings.choose_program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository

class ChooseProgramViewModel (private val repository: ProgramRepository) : ViewModel() {

    val programs = repository.programsFlow().asLiveData()
}
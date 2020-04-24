package com.dmitrysimakov.kilogram.ui.search.public_programs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository

class PublicProgramsViewModel (repository: ProgramRepository) : ViewModel() {

    val programs = liveData { emit(repository.publicPrograms()) }
}
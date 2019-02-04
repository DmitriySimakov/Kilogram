package com.dmitrysimakov.kilogram.ui.exercises.choose_muscle

import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.repository.MuscleRepository
import javax.inject.Inject

class ChooseMuscleViewModel @Inject constructor(private val repository: MuscleRepository) : ViewModel() {

    val muscleList = repository.loadMuscleList()
}
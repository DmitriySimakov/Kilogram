package com.dmitrysimakov.kilogram.ui.training

import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import javax.inject.Inject

class TrainingViewModel @Inject constructor(private val repository: TrainingRepository) : ViewModel() {

}
package com.dmitrysimakov.kilogram.ui.training.trainingList

import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import javax.inject.Inject


class TrainingsViewModel @Inject constructor(private val repository: TrainingRepository) : ViewModel() {

    val trainingList = repository.loadTrainingList()
}
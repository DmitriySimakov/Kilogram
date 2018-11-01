package com.dmitrysimakov.kilogram.ui.trainings

import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import javax.inject.Inject


class TrainingsViewModel @Inject constructor(private val repository: TrainingRepository) : ViewModel() {

    val trainingList = repository.loadTrainingList()
}
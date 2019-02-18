package com.dmitrysimakov.kilogram.ui.trainings.trainings

import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.Training
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import javax.inject.Inject


class TrainingsViewModel @Inject constructor(private val repository: TrainingRepository) : ViewModel() {

    val trainingList = repository.loadTrainingList()

    fun deleteTraining(training: Training) {
        repository.deleteTraining(training)
    }
}
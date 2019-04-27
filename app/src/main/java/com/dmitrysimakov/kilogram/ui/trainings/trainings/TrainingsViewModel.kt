package com.dmitrysimakov.kilogram.ui.trainings.trainings

import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.Training
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import java.util.*
import javax.inject.Inject

class TrainingsViewModel @Inject constructor(private val repository: TrainingRepository) : ViewModel() {

    val trainingList = repository.loadTrainingRList()

    fun findPositionInTrainingList(calendar: Calendar): Int {
        val ms = calendar.time.time
        val pos = trainingList.value?.indexOfFirst { it.start_time < ms }
        return if (pos == null || pos == -1) 0 else pos
    }
    
    fun deleteTraining(id: Long) {
        repository.deleteTraining(id)
    }
}
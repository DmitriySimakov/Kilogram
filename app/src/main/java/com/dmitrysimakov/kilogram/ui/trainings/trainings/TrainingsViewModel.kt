package com.dmitrysimakov.kilogram.ui.trainings.trainings

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.relation.DetailedTraining
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TrainingsViewModel (private val repository: TrainingRepository) : ViewModel() {

    val detailedTrainings = liveData { emit(repository.loadDetailedTrainingList()) }
    
    fun findPositionInTrainingList(calendar: Calendar): Int {
        val ms = calendar.time.time
        val pos = detailedTrainings.value?.indexOfFirst { it.start_time < ms }
        return if (pos == null || pos == -1) 0 else pos
    }
    
    fun deleteTraining(id: Long) = viewModelScope.launch {
        repository.deleteTraining(id)
    }
}
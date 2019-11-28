package com.dmitrysimakov.kilogram.ui.home.trainings.trainings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

class TrainingsViewModel (private val repository: TrainingRepository) : ViewModel() {

    val detailedTrainings = repository.detailedTrainingsFlow().asLiveData()
    
    fun findPositionInTrainingList(date: LocalDate): Int {
        val dateTime = OffsetDateTime.of(date, LocalTime.MIN, ZoneOffset.UTC)
        val pos = detailedTrainings.value?.indexOfFirst { it.start_date_time < dateTime }
        return if (pos == null || pos == -1) 0 else pos
    }
    
    fun deleteTraining(id: Long) = viewModelScope.launch {
        repository.delete(id)
    }
}
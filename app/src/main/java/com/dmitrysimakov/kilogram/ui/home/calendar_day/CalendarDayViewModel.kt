package com.dmitrysimakov.kilogram.ui.home.calendar_day

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import org.threeten.bp.LocalDate

class CalendarDayViewModel(
        private val trainingRepo: TrainingRepository
) : ViewModel() {

    val date = MutableLiveData<LocalDate>()
    
    val trainings = date.switchMap { liveData {
        emit(trainingRepo.detailedTrainingsForDay(it))
    }}
}
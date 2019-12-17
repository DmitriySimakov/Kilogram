package com.dmitrysimakov.kilogram.ui.home.calendar_day_overview

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import org.threeten.bp.LocalDate

class CalendarDayViewModel(
        private val trainingRepo: TrainingRepository
) : ViewModel() {

    private val _date = MutableLiveData<LocalDate>()
    val date: LiveData<LocalDate> = _date
    
    val trainings = date.switchMap { liveData {
        emit(trainingRepo.detailedTrainingsForDay(it))
    }}
    
    fun setDate(date: LocalDate) = _date.setNewValue(date)
}
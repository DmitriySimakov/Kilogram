package com.dmitrysimakov.kilogram.ui.home.calendar_day_overview

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import java.util.*

class CalendarDayOverviewViewModel(
        private val trainingRepo: TrainingRepository
) : ViewModel() {

    private val _calendar = MutableLiveData<Calendar>()
    val calendar: LiveData<Calendar> = _calendar
    
    val trainings = calendar.switchMap { liveData {
        emit(trainingRepo.detailedTrainingsForDay(it))
    }}
    
    fun setCalendar(calendar: Calendar) = _calendar.setNewValue(calendar)
}
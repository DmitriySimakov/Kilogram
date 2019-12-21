package com.dmitrysimakov.kilogram.ui.home.measurements

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import org.threeten.bp.LocalDate

class MeasurementsViewModel (private val repository: MeasurementRepository) : ViewModel() {
    
    private val _date = MutableLiveData<LocalDate>()
    
    val measurements = _date.switchMap {
        repository.measurementsWithPreviousResults(it).asLiveData()
    }
    
    fun setDate(date: LocalDate) { _date.setNewValue(date) }
}
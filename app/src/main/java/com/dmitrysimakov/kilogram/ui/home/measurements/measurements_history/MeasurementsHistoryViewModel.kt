package com.dmitrysimakov.kilogram.ui.home.measurements.measurements_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository

class MeasurementsHistoryViewModel (
        private val repository: MeasurementRepository
) : ViewModel() {

    val measurementDates = repository.measurementDates().asLiveData()
}

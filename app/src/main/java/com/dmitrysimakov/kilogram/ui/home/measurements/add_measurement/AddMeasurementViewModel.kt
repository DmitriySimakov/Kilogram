package com.dmitrysimakov.kilogram.ui.home.measurements.add_measurement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.model.Measurement
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddMeasurementViewModel (
        private val repository: MeasurementRepository
) : ViewModel() {

    val date = MutableLiveData<Date>()
    
    val measurements = date.switchMap { date -> liveData { emit(repository.measurements(date)) }}
    
    fun addMeasurements() = CoroutineScope(Dispatchers.IO).launch {
        measurements.value?.forEach { measurement ->
            measurement.value?.let { repository.insert(Measurement(measurement.param, it, date.value!!)) }
        }
    }
}

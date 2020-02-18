package com.dmitrysimakov.kilogram.ui.home.measurements

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository
import com.dmitrysimakov.kilogram.util.live_data.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MeasurementsViewModel (private val repository: MeasurementRepository) : ViewModel() {
    
    val date = MutableLiveData<Date>()
    
    val measurements = date.switchMap {
        repository.measurementsWithPreviousResults(it).asLiveData()
    }
    
    val measurementsDeletedEvent = MutableLiveData<Event<Unit>>()
    
    fun deleteMeasurements() {
        val measurements = measurements.value ?: return
        CoroutineScope(Dispatchers.IO).launch {
            measurements.forEach { repository.delete(it.id!!) }
        }
        measurementsDeletedEvent.value = Event(Unit)
    }
}
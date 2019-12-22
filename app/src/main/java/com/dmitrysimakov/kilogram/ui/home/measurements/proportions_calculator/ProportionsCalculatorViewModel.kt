package com.dmitrysimakov.kilogram.ui.home.measurements.proportions_calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.local.relation.ProportionsCalculatorItem
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository
import com.dmitrysimakov.kilogram.util.round
import kotlinx.coroutines.launch

class ProportionsCalculatorViewModel (private val repository: MeasurementRepository) : ViewModel() {
    
    private val _calculatorItems = MutableLiveData<List<ProportionsCalculatorItem>>()
    
    val calculatorItems = _calculatorItems.map { list -> list.map { item ->
        val chest = list.find { it.coefficient == 1.0 }!!
        val recommendedValue = chest.value?.let { it * item.coefficient }?.round(1)
        ProportionsCalculatorItem(item.param, item.coefficient, item.value, recommendedValue)
    }}
    
    init { viewModelScope.launch {
        _calculatorItems.value = repository.lastMeasurementsWithCoefficients()
    }}
    
    fun updateValue(item: ProportionsCalculatorItem, value: CharSequence) {
        _calculatorItems.value = _calculatorItems.value?.map {
            if (it.param == item.param) {
                val v = try { value.toString().toDouble() } catch (e: Exception) { null }
                ProportionsCalculatorItem(it.param, it.coefficient, v, it.recommendedValue)
            } else it
        }
    }
}

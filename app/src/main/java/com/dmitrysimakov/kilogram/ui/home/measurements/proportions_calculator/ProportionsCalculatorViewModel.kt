package com.dmitrysimakov.kilogram.ui.home.measurements.proportions_calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.relation.ProportionsCalculatorItem
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository
import com.dmitrysimakov.kilogram.util.round
import kotlinx.coroutines.launch

class ProportionsCalculatorViewModel (private val repository: MeasurementRepository) : ViewModel() {
    
    private val _calculatorItems = MutableLiveData<List<ProportionsCalculatorItem>>()
    val calculatorItems = _calculatorItems.map { list ->
        val notNullValueItem = list.find { it.value != null } ?: return@map list
        
        val listRelativeByItem = list.calculateParamsRelativeBy(notNullValueItem)
        
        val minPercentNotWaistItem = listRelativeByItem
                .filter { it.param != "Талия" && it.percent != null }
                .minBy { it.percent!! }
        val waistItem = listRelativeByItem.find { it.param == "Талия" }
        val minPercentItem = minPercentNotWaistItem ?: waistItem
        if (minPercentItem == null || minPercentItem.param == notNullValueItem.param) return@map list

        // return list relative by minPercentItem
        return@map listRelativeByItem.calculateParamsRelativeBy(minPercentItem)
    }
    
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
    
    private fun List<ProportionsCalculatorItem>.calculateParamsRelativeBy(relativeItem: ProportionsCalculatorItem) = map { curItem ->
        val curItemValue = curItem.value ?: return@map curItem
        val relativeItemValue = relativeItem.value ?: return@map curItem
        
        val recommendedValue = (relativeItemValue * curItem.coefficient / relativeItem.coefficient).round(1)
        val percent = ((recommendedValue / curItemValue - 1) * 100).round(1)
        
        curItem.copy(recommendedValue = recommendedValue, percent = percent)
    }
}

package com.dmitrysimakov.kilogram. ui.training.addSet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.TrainingExerciseSet
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import javax.inject.Inject

class AddSetViewModel @Inject constructor(
        private val repository: TrainingRepository
) : ViewModel() {

    private val _setId = MutableLiveData<Long>()
    
    val set = Transformations.switchMap(_setId) { id ->
        when (id) {
            null -> AbsentLiveData.create()
            0L -> MutableLiveData(TrainingExerciseSet(
                    0, 0, 0, 0, 0, 0, 0))
            else -> repository.loadSet(id)
        }
    }
    
    fun setSet(id: Long) {
        if (_setId.value != id) {
            _setId.value = id
        }
    }
    
    fun addSet(set: TrainingExerciseSet) {
        repository.insertSet(set)
    }
    
    fun updateSet(set: TrainingExerciseSet) {
        repository.updateSet(set)
    }
}

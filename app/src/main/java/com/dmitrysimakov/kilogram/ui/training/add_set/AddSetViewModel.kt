package com.dmitrysimakov.kilogram. ui.training.add_set

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
    private val _trainingExerciseId = MutableLiveData<Long>()
    
    val set = Transformations.switchMap(_setId) { id ->
        when (id) {
            null -> AbsentLiveData.create()
            0L -> MutableLiveData(TrainingExerciseSet(0, _trainingExerciseId.value!!, 0, 0, 0, 0, 0))
            else -> repository.loadSet(id)
        }
    }
    
    fun setParams(setId: Long, trainingExerciseId: Long) {
        if (_trainingExerciseId.value != trainingExerciseId) {
            _trainingExerciseId.value = trainingExerciseId
        }
        if (_setId.value != setId) {
            _setId.value = setId
        }
    }
    
    fun addSet() {
        set.value?.let { repository.insertSet(it) }
    }
    
    fun updateSet() {
        set.value?.let { repository.updateSet(it) }
    }
}

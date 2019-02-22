package com.dmitrysimakov.kilogram. ui.trainings.add_set

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.TrainingExerciseSet
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import com.dmitrysimakov.kilogram.util.setNewValue
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
    
    val exerciseMeasures = Transformations.switchMap(_trainingExerciseId) { id ->
        when (id) {
            null -> AbsentLiveData.create()
            else -> repository.loadExerciseMeasures(id)
        }
    }
    
    fun setParams(setId: Long, trainingExerciseId: Long) {
        _trainingExerciseId.setNewValue(trainingExerciseId)
        _setId.setNewValue(setId)
    }
    
    fun addSet() {
        set.value?.let { repository.insertSet(it) }
    }
    
    fun updateSet() {
        set.value?.let { repository.updateSet(it) }
    }
    
    fun decreaseWeight() {
        set.value?.weight = set.value?.weight?.minus(5)?.coerceAtLeast(0)
    }
    
    fun increaseWeight() {
        set.value?.weight = set.value?.weight?.plus(5)
    }
    
    fun decreaseReps() {
        set.value?.reps = set.value?.reps?.minus(1)?.coerceAtLeast(0)
    }
    
    fun increaseReps() {
        set.value?.reps = set.value?.reps?.plus(1)
    }
    
    fun decreaseTime() {
        set.value?.time = set.value?.time?.minus(15)?.coerceAtLeast(0)
    }
    
    fun increaseTime() {
        set.value?.time = set.value?.time?.plus(15)
    }
    
    fun decreaseDistance() {
        set.value?.distance = set.value?.distance?.minus(1)?.coerceAtLeast(0)
    }
    
    fun increaseDistance() {
        set.value?.distance = set.value?.distance?.plus(1)
    }
}

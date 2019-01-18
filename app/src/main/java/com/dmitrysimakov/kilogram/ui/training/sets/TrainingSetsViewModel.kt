package com.dmitrysimakov.kilogram.ui.training.sets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.TrainingExerciseSet
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import javax.inject.Inject

class TrainingSetsViewModel @Inject constructor(private val repository: TrainingRepository) : ViewModel() {

    private val _id = MutableLiveData<Long>()
    val id: LiveData<Long>
        get() = _id

    val sets = Transformations.switchMap(_id) { id ->
        if (id == null) {
            AbsentLiveData.create()
        } else {
            repository.loadSets(id)
        }
    }

    fun setExercise(id: Long) {
        if (_id.value != id) {
            _id.value = id
        }
    }

    fun deleteSet(set: TrainingExerciseSet) {
        repository.deleteSet(set)
    }
}
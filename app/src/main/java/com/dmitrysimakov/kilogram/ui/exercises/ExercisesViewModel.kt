package com.dmitrysimakov.kilogram.ui.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import javax.inject.Inject

class ExercisesViewModel @Inject constructor(private val repository: ExerciseRepository) : ViewModel() {

    private val _muscleId = MutableLiveData<Long>()
    val muscleId: LiveData<Long>
        get() = _muscleId

    val exerciseList : LiveData<List<Exercise>> = Transformations
            .switchMap(_muscleId) { muscleId ->
        if (muscleId == null) {
            AbsentLiveData.create()
        } else {
            repository.loadExerciseList(muscleId)
        }
    }

    fun setMuscle(id: Long?) {
        if (_muscleId.value != id) {
            _muscleId.value = id
        }
    }
}

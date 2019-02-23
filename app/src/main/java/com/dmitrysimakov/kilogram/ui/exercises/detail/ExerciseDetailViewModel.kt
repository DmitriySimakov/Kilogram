package com.dmitrysimakov.kilogram.ui.exercises.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.relation.ExerciseR
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import com.dmitrysimakov.kilogram.util.setNewValue
import javax.inject.Inject

class ExerciseDetailViewModel @Inject constructor(private val repository: ExerciseRepository) : ViewModel() {

    private val _exerciseId = MutableLiveData<Long>()

    val exercise = Transformations.switchMap(_exerciseId) {
        repository.loadExerciseR(it)
    }

    fun setExercise(id: Long?) {
        _exerciseId.setNewValue(id)
    }
}
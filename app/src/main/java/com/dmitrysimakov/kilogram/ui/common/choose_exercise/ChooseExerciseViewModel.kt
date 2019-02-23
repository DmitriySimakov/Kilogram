package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import javax.inject.Inject

class ChooseExerciseViewModel @Inject constructor(private val repository: ExerciseRepository) : ViewModel() {

    private val _muscleId = MutableLiveData<Long>()

    val exerciseList = Transformations.switchMap(_muscleId) {
        repository.loadExerciseList(it)
    }

    fun setMuscle(id: Long) {
        _muscleId.setNewValue(id)
    }
}

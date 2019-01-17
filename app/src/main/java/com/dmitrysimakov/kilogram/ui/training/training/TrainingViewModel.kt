package com.dmitrysimakov.kilogram.ui.training.training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.data.entity.Training
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import javax.inject.Inject

class TrainingViewModel @Inject constructor(private val repository: TrainingRepository) : ViewModel() {

    private val _id = MutableLiveData<Long>()
    val id: LiveData<Long>
        get() = _id

    val exercises = Transformations.switchMap(_id) { id ->
        if (id == null) {
            AbsentLiveData.create()
        } else {
            repository.loadExercises(id)
        }
    }

    fun setTraining(id: Long) {
        if (_id.value != id) {
            _id.value = id
        }
    }

    fun deleteExercise(exercise: Exercise, training_id: Long) {
        repository.deleteExercise(exercise, training_id)
    }
}
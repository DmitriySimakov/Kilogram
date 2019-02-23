package com.dmitrysimakov.kilogram.ui.programs.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.relation.ProgramExerciseR
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import com.dmitrysimakov.kilogram.util.setNewValue
import javax.inject.Inject

class ProgramDayExercisesViewModel @Inject constructor(private val repository: ProgramRepository) : ViewModel() {

    private val _programDayId = MutableLiveData<Long>()
    
    val exercises = Transformations.switchMap(_programDayId) {
        repository.loadExercises(it)
    }
    
    fun setProgramDay(id: Long) {
        _programDayId.setNewValue(id)
    }

    fun deleteExercise(exercise: ProgramExerciseR) {
        repository.deleteExerciseFromProgramDay(exercise)
    }
}
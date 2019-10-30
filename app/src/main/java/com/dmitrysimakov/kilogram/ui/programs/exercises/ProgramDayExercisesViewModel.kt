package com.dmitrysimakov.kilogram.ui.programs.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import timber.log.Timber

class ProgramDayExercisesViewModel (private val repository: ProgramDayExerciseRepository) : ViewModel() {
    
    private val _programDayId = MutableLiveData<Long>()
    
    val exercises = Transformations.switchMap(_programDayId) {
        Timber.d("plannedExercises load")
        repository.loadProgramDayExerciseList(it)
    }
    
    fun setProgramDay(id: Long) {
        _programDayId.setNewValue(id)
    }

    fun deleteExercise(exercise: ProgramDayExercise) {
        repository.deleteExerciseFromProgramDay(exercise)
    }
    
    fun updateNums() {
        exercises.value?.let { list ->
            list.forEachIndexed { index, exercise -> exercise.indexNumber = index + 1 }
            repository.update(list)
        }
    }
}
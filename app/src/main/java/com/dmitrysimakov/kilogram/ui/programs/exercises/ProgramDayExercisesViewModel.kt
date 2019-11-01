package com.dmitrysimakov.kilogram.ui.programs.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch
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

    fun deleteExercise(exercise: ProgramDayExercise) { viewModelScope.launch {
        repository.deleteExerciseFromProgramDay(exercise)
    }}
    
    fun updateIndexNumbers() { viewModelScope.launch {
        exercises.value?.let { list ->
            list.forEachIndexed { index, exercise -> exercise.indexNumber = index + 1 }
            repository.update(list)
        }
    }}
}
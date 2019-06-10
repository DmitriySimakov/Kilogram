package com.dmitrysimakov.kilogram.ui.programs.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.relation.ProgramExerciseR
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import timber.log.Timber
import javax.inject.Inject

class ProgramDayExercisesViewModel @Inject constructor(
        private val repository: ProgramDayExerciseRepository
) : ViewModel() {
    
    private val _programDayId = MutableLiveData<Long>()
    
    val exercises = Transformations.switchMap(_programDayId) {
        Timber.d("plannedExercises load")
        repository.loadExercises(it)
    }
    
    fun setProgramDay(id: Long) {
        _programDayId.setNewValue(id)
    }

    fun deleteExercise(exercise: ProgramExerciseR) {
        repository.deleteExerciseFromProgramDay(exercise)
    }
    
    fun updateNums(items: List<ProgramExerciseR>) {
        repository.updateNums(items)
    }
}
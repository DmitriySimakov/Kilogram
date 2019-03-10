package com.dmitrysimakov.kilogram.ui.programs.exercises

import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.relation.ProgramExerciseR
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import javax.inject.Inject

class ProgramDayExercisesViewModel @Inject constructor(
        private val repository: ProgramDayExerciseRepository
) : ViewModel() {
    
    private val TAG = this::class.java.simpleName
    
    private val _programDayId = MutableLiveData<Long>()
    
    val exercises = Transformations.switchMap(_programDayId) {
        d(TAG, "plannedExercises load")
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
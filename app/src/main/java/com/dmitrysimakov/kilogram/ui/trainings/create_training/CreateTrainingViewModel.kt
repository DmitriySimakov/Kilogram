package com.dmitrysimakov.kilogram.ui.trainings.create_training

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.local.entity.TrainingMuscle
import com.dmitrysimakov.kilogram.data.repository.*
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch
import java.util.*

class CreateTrainingViewModel(
        private val trainingRepo: TrainingRepository,
        private val trainingExerciseRepo: TrainingExerciseRepository,
        private val programDayRepo: ProgramDayRepository,
        private val exerciseRepo: ExerciseRepository,
        private val trainingMuscleRepo: TrainingMuscleRepository
) : ViewModel() {
    
    private val _calendar = MutableLiveData<Calendar>(Calendar.getInstance())
    fun updateCalendar() { _calendar.value = _calendar.value }
    val calendar: LiveData<Calendar>
        get() = _calendar
    
    val byProgram = MutableLiveData(false)
    
    fun setProgramDay(id: Long) { _programDayId.setNewValue(id) }
    private val _programDayId = MutableLiveData<Long>()
    val programDay = _programDayId.switchMap {
        when (it) {
            0L -> programDayRepo.loadNextProgramDayAndProgram()
            else -> programDayRepo.loadProgramDayAndProgram(it)
        }
    }
    
    fun createTraining(callback: ((Long) -> Unit)) { viewModelScope.launch {
        val programDayId = if (byProgram.value == true) _programDayId.value else null
        trainingRepo.insertTraining(Training(0, calendar.value!!.timeInMillis, null, programDayId), callback)
    }}
    
    fun fillTrainingWithProgramExercises(trainingId: Long) { viewModelScope.launch {
        _programDayId.value?.let { trainingExerciseRepo.fillTrainingWithProgramExercises(trainingId, it) }
    }}
    
    
    val muscleList = _programDayId.switchMap {
        when (it) {
            0L -> exerciseRepo.loadMuscleParams()
            else -> exerciseRepo.loadMuscleParams(it)
        }
    }
    
    fun saveMuscles(trainingId: Long) { viewModelScope.launch {
        val list = mutableListOf<TrainingMuscle>()
        for (muscle in muscleList.value!!) {
            if (muscle.is_active) list.add(TrainingMuscle(trainingId, muscle.name))
        }
        trainingMuscleRepo.insert(list)
    }}
}
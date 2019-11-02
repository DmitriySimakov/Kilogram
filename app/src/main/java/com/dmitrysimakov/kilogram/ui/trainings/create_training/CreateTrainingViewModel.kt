package com.dmitrysimakov.kilogram.ui.trainings.create_training

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.dao.MuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.local.entity.TrainingMuscle
import com.dmitrysimakov.kilogram.data.repository.*
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch
import java.util.*

class CreateTrainingViewModel(
        muscleDao: MuscleDao,
        private val trainingRepo: TrainingRepository,
        private val trainingExerciseRepo: TrainingExerciseRepository,
        private val programDayRepo: ProgramDayRepository,
        private val trainingMuscleRepo: TrainingMuscleRepository
) : ViewModel() {
    
    private val _calendar = MutableLiveData<Calendar>(Calendar.getInstance())
    val calendar: LiveData<Calendar> = _calendar
    fun updateCalendar() { _calendar.value = _calendar.value }
    
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
            0L -> muscleDao.getParamList()
            else -> muscleDao.getProgramDayParamList(it)
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
package com.dmitrysimakov.kilogram.ui.trainings.create_training

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.dao.MuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.local.entity.TrainingMuscle
import com.dmitrysimakov.kilogram.data.relation.FilterParam
import com.dmitrysimakov.kilogram.data.relation.ProgramDayAndProgram
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingMuscleRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.Event
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CreateTrainingViewModel(
        private val muscleDao: MuscleDao,
        private val trainingRepo: TrainingRepository,
        private val trainingExerciseRepo: TrainingExerciseRepository,
        private val programDayRepo: ProgramDayRepository,
        private val trainingMuscleRepo: TrainingMuscleRepository
) : ViewModel() {
    
    private val _calendar = MutableLiveData<Calendar>(Calendar.getInstance())
    val calendar: LiveData<Calendar> = _calendar
    fun updateCalendar() { _calendar.value = _calendar.value }
    
    val byProgram = MutableLiveData(false)
    
    private val _programDay = MutableLiveData<ProgramDayAndProgram>()
    val programDay: LiveData<ProgramDayAndProgram> = _programDay
    
    private val _muscleList = MutableLiveData<List<FilterParam>>()
    val muscleList: LiveData<List<FilterParam>> = _muscleList
    
    private val _trainingCreatedEvent = MutableLiveData<Event<Long>>()
    val trainingCreatedEvent: LiveData<Event<Long>> = _trainingCreatedEvent
    
    fun setProgramDay(programDayId: Long) = viewModelScope.launch {
        _programDay.value = if (programDayId == 0L) {
            programDayRepo.loadNextProgramDayAndProgram()
        } else {
            programDayRepo.loadProgramDayAndProgram(programDayId)
        }
        
        _muscleList.value = if (programDayId == 0L) {
            muscleDao.getParamList()
        } else {
            muscleDao.getProgramDayParamList(programDayId)
        }
    }
    
    fun createTraining() = viewModelScope.launch{
        val programDayId = byProgram.value?.let { programDay.value?.program_day_id }
        val trainingId = trainingRepo.insertTraining(
                Training(0, calendar.value!!.timeInMillis, null, programDayId)
        )
        if (byProgram.value == true) fillTrainingWithProgramExercises(trainingId)
        saveMuscles(trainingId)
        _trainingCreatedEvent.value = Event(trainingId)
    }
    
    private suspend fun fillTrainingWithProgramExercises(trainingId: Long) = viewModelScope.launch {
        programDay.value?.program_day_id?.let {
            trainingExerciseRepo.fillTrainingWithProgramExercises(trainingId, it)
        }
    }
    
    private suspend fun saveMuscles(trainingId: Long) = viewModelScope.launch {
        val list = mutableListOf<TrainingMuscle>()
        for (muscle in muscleList.value!!) {
            if (muscle.is_active) list.add(TrainingMuscle(trainingId, muscle.name))
        }
        trainingMuscleRepo.insert(list)
    }
}
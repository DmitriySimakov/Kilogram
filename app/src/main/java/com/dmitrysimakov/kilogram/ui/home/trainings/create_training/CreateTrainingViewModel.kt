package com.dmitrysimakov.kilogram.ui.home.trainings.create_training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.local.dao.ExerciseTargetDao
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.local.entity.TrainingTarget
import com.dmitrysimakov.kilogram.data.local.relation.FilterParam
import com.dmitrysimakov.kilogram.data.local.relation.ProgramDayAndProgram
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingMuscleRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.Event
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime

class CreateTrainingViewModel(
        private val exerciseTargetDao: ExerciseTargetDao,
        private val trainingRepo: TrainingRepository,
        private val trainingExerciseRepo: TrainingExerciseRepository,
        private val programDayRepo: ProgramDayRepository,
        private val trainingMuscleRepo: TrainingMuscleRepository
) : ViewModel() {
    
    private val _dateTime = MutableLiveData(OffsetDateTime.now())
    val dateTime: LiveData<OffsetDateTime> = _dateTime
    fun setDateTime(dateTime: OffsetDateTime) { _dateTime.setNewValue(dateTime) }
    
    val byProgram = MutableLiveData(false)
    
    private val _programDay = MutableLiveData<ProgramDayAndProgram>()
    val programDay: LiveData<ProgramDayAndProgram> = _programDay
    
    private val _muscleList = MutableLiveData<List<FilterParam>>()
    val muscleList: LiveData<List<FilterParam>> = _muscleList
    
    private val _trainingCreatedEvent = MutableLiveData<Event<Long>>()
    val trainingCreatedEvent: LiveData<Event<Long>> = _trainingCreatedEvent
    
    fun setProgramDay(programDayId: Long) = viewModelScope.launch {
        _programDay.value = if (programDayId == 0L) {
            programDayRepo.nextProgramDayAndProgram()
        } else {
            programDayRepo.programDayAndProgram(programDayId)
        }
        
        _muscleList.value = if (programDayId == 0L) {
            exerciseTargetDao.params()
        } else {
            exerciseTargetDao.programDayParams(programDayId)
        }
    }
    
    fun createTraining() = viewModelScope.launch{
        val programDayId = byProgram.value?.let { programDay.value?.program_day_id }
        val trainingId = trainingRepo.insert(Training(0, dateTime.value!!, null, programDayId))
        if (byProgram.value == true) fillTrainingWithProgramExercises(trainingId)
        saveMuscles(trainingId)
        _trainingCreatedEvent.value = Event(trainingId)
    }
    
    private suspend fun fillTrainingWithProgramExercises(trainingId: Long) = viewModelScope.launch {
        programDay.value?.program_day_id?.let { programDayId ->
            trainingExerciseRepo.fillTrainingWithProgramExercises(trainingId, programDayId)
        }
    }
    
    private suspend fun saveMuscles(trainingId: Long) = viewModelScope.launch {
        val list = mutableListOf<TrainingTarget>()
        for (muscle in muscleList.value!!) {
            if (muscle.is_active) list.add(TrainingTarget(trainingId, muscle.name))
        }
        trainingMuscleRepo.insert(list)
    }
}
package com.dmitrysimakov.kilogram.ui.home.trainings.create_training

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.Event
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime

class CreateTrainingViewModel(
        private val trainingRepo: TrainingRepository,
        private val trainingExerciseRepo: TrainingExerciseRepository,
        private val programDayRepo: ProgramDayRepository
) : ViewModel() {
    
    private val _dateTime = MutableLiveData(OffsetDateTime.now())
    val dateTime: LiveData<OffsetDateTime> = _dateTime
    
    private val _programDayId = MutableLiveData<Long>()
    
    val byProgram = MutableLiveData(false)
    
    val programDay = _programDayId.switchMap { programDayId -> liveData {
        when (programDayId) {
            0L -> emit(programDayRepo.nextProgramDayAndProgram())
            else -> emit(programDayRepo.programDayAndProgram(programDayId))
        }
    }}
    
    private val _trainingCreatedEvent = MutableLiveData<Event<Long>>()
    val trainingCreatedEvent: LiveData<Event<Long>> = _trainingCreatedEvent
    
    fun setDateTime(dateTime: OffsetDateTime) { _dateTime.setNewValue(dateTime) }
    
    fun setProgramDay(programDayId: Long) { _programDayId.setNewValue(programDayId) }
    
    fun createTraining() = viewModelScope.launch{
        val programDayId = byProgram.value?.let { programDay.value?.program_day_id }
        val trainingId = trainingRepo.insert(Training(0, dateTime.value!!, null, programDayId))
        if (byProgram.value == true) fillTrainingWithProgramExercises(trainingId)
        _trainingCreatedEvent.value = Event(trainingId)
    }
    
    private suspend fun fillTrainingWithProgramExercises(trainingId: Long) = viewModelScope.launch {
        programDay.value?.program_day_id?.let { programDayId ->
            trainingExerciseRepo.fillTrainingWithProgramExercises(trainingId, programDayId)
        }
    }
}
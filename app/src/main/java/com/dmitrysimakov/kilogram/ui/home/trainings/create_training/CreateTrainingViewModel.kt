package com.dmitrysimakov.kilogram.ui.home.trainings.create_training

import android.app.Application
import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.live_data.Event
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime

class CreateTrainingViewModel(
        app: Application,
        private val trainingRepo: TrainingRepository,
        private val trainingExerciseRepo: TrainingExerciseRepository,
        private val programDayRepo: ProgramDayRepository,
        private val programDayExerciseRepo: ProgramDayExerciseRepository
) : AndroidViewModel(app) {
    
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
        val programDayId = byProgram.value?.let { programDay.value?.programDayId }
        val training = Training(0, dateTime.value!!, null, programDayId)
        val trainingId = trainingRepo.insert(training)
        if (byProgram.value == true) fillTrainingWithProgramExercises(trainingId)
        
        _trainingCreatedEvent.value = Event(trainingId)
    }
    
    private suspend fun fillTrainingWithProgramExercises(trainingId: Long) {
        val programDayId = programDay.value?.programDayId ?: return
        viewModelScope.launch {
            val programDayExercises = programDayExerciseRepo.programDayExercises(programDayId)
            val trainingExercises = programDayExercises.map { TrainingExercise(0, trainingId, it.exercise, it.indexNumber, it.rest, it.strategy) }
            trainingExerciseRepo.insert(trainingExercises)
        }
    }
}
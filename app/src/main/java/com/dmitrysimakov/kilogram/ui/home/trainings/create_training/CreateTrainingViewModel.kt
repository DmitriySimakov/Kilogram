package com.dmitrysimakov.kilogram.ui.home.trainings.create_training

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.local.entity.Program
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.live_data.Event
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime

class CreateTrainingViewModel(
        private val trainingRepo: TrainingRepository,
        private val trainingExerciseRepo: TrainingExerciseRepository,
        private val programDayExerciseRepo: ProgramDayExerciseRepository
) : ViewModel() {
    
    val dateTime = MutableLiveData(OffsetDateTime.now())
    
    val program = MutableLiveData<Program>()
    val programDay = MutableLiveData<ProgramDay>()
    
    val byProgram = MutableLiveData(false)
    
    val trainingCreatedEvent = MutableLiveData<Event<Long>>()
    
    fun createTraining() = viewModelScope.launch{
        val programDayId = byProgram.value?.let { programDay.value?.id }
        val training = Training(0, dateTime.value!!, null, programDayId)
        val trainingId = trainingRepo.insert(training)
        if (programDayId != null) fillTrainingWithProgramExercises(trainingId, programDayId)
        
        trainingCreatedEvent.value = Event(trainingId)
    }
    
    private suspend fun fillTrainingWithProgramExercises(trainingId: Long, programDayId: Long) { viewModelScope.launch {
        val programDayExercises = programDayExerciseRepo.programDayExercises(programDayId)
        val trainingExercises = programDayExercises.map {
            TrainingExercise(0, trainingId, it.exercise, it.indexNumber, it.rest, it.strategy)
        }
        trainingExerciseRepo.insert(trainingExercises)
    }}
}
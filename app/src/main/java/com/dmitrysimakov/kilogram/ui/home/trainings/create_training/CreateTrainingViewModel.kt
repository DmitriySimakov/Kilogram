package com.dmitrysimakov.kilogram.ui.home.trainings.create_training

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.model.Program
import com.dmitrysimakov.kilogram.data.model.ProgramDay
import com.dmitrysimakov.kilogram.data.model.Training
import com.dmitrysimakov.kilogram.data.model.TrainingExercise
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.live_data.Event
import com.dmitrysimakov.kilogram.util.toDate
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime

class CreateTrainingViewModel(
        private val trainingRepo: TrainingRepository,
        private val trainingExerciseRepo: TrainingExerciseRepository,
        private val programDayExerciseRepo: ProgramDayExerciseRepository
) : ViewModel() {
    
    val dateTime = MutableLiveData(LocalDateTime.now())
    
    val program = MutableLiveData<Program>()
    val programDay = MutableLiveData<ProgramDay>()
    
    val byProgram = MutableLiveData(false)
    
    val trainingCreatedEvent = MutableLiveData<Event<String>>()
    
    fun createTraining() = viewModelScope.launch{
        val programDayId = byProgram.value?.let { programDay.value?.id }
        val training = Training(dateTime.value!!.toDate(), programDayId)
        trainingRepo.insert(training)
        if (programDayId != null) fillTrainingWithProgramExercises(training.id, programDayId)
        
        trainingCreatedEvent.value = Event(training.id)
    }
    
    private suspend fun fillTrainingWithProgramExercises(trainingId: String, programDayId: String) { viewModelScope.launch {
        val programDayExercises = programDayExerciseRepo.programDayExercises(programDayId)
        val trainingExercises = programDayExercises.map {
            TrainingExercise(trainingId, it.exercise, it.indexNumber, it.rest, it.strategy)
        }
        trainingExerciseRepo.insert(trainingExercises)
    }}
}
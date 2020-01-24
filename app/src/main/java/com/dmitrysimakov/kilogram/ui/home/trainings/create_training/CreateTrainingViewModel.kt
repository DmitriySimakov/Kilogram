package com.dmitrysimakov.kilogram.ui.home.trainings.create_training

import android.app.Application
import androidx.lifecycle.*
import androidx.work.*
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.Event
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.workers.UploadTrainingWorker
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime


class CreateTrainingViewModel(
        app: Application,
        private val trainingRepo: TrainingRepository,
        private val trainingExerciseRepo: TrainingExerciseRepository,
        private val programDayRepo: ProgramDayRepository
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
        val programDayId = byProgram.value?.let { programDay.value?.program_day_id }
        val training = Training(0, dateTime.value!!, null, programDayId)
        val trainingId = trainingRepo.insert(training)
        if (byProgram.value == true) fillTrainingWithProgramExercises(trainingId)
        uploadTraining(trainingId)
        
        _trainingCreatedEvent.value = Event(trainingId)
    }
    
    private fun uploadTraining(id: Long) {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val data = Data.Builder().putLong("id", id).build()
        
        val uploadTrainingRequest = OneTimeWorkRequest.Builder(UploadTrainingWorker::class.java)
                .setConstraints(constraints)
                .setInputData(data)
                .build()
    
        WorkManager.getInstance(getApplication()).enqueue(uploadTrainingRequest)
    }
    
    private suspend fun fillTrainingWithProgramExercises(trainingId: Long) = viewModelScope.launch {
        programDay.value?.program_day_id?.let { programDayId ->
            trainingExerciseRepo.fillTrainingWithProgramExercises(trainingId, programDayId)
        }
    }
}
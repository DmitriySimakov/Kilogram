package com.dmitrysimakov.kilogram.ui.trainings.create_training

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.data.entity.Training
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import com.dmitrysimakov.kilogram.util.AbsentLiveData
import com.dmitrysimakov.kilogram.util.setNewValue
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CreateTrainingViewModel @Inject constructor(
        private val trainingRepository: TrainingRepository,
        private val programRepository: ProgramRepository
) : ViewModel() {

    val calendar: Calendar = Calendar.getInstance()
    val date = MutableLiveData<String>()
    val time = MutableLiveData<String>()
    
    init {
        val locale = Locale.getDefault()
        date.value = SimpleDateFormat("yyyy-MM-dd", locale).format(calendar.time)
        time.value = SimpleDateFormat("HH:mm:ss", locale).format(calendar.time)
    }
    
    val byProgram = MutableLiveData(true)

    private val _chosenProgramDayId = MutableLiveData<Long>()
    
    val programDay = Transformations.switchMap(_chosenProgramDayId) {
        when (it) {
            0L -> programRepository.loadNextProgramDayR()
            else -> programRepository.loadProgramDayR(it)
        }
    }
    
    fun setProgramDay(id: Long) {
        _chosenProgramDayId.setNewValue(id)
    }

    fun createTraining(callback: ItemInsertedListener) {
        val programDayId = if (byProgram.value!!) programDay.value?.program_day_id else null
        trainingRepository.insertTraining(Training(0,"${date.value} ${time.value}", programDayId), callback)
    }
    
    fun fillTrainingWithProgramExercises(trainingId: Long) {
        programDay.value?.let { trainingRepository.fillTrainingWithProgramExercises(trainingId, it.program_day_id) }
    }
}
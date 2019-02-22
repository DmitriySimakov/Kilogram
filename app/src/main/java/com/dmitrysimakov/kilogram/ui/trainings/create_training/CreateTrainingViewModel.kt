package com.dmitrysimakov.kilogram.ui.trainings.create_training

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.data.entity.Training
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
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

    val byProgram = MutableLiveData<Boolean>()

    private val _programDayId = MutableLiveData<Long>()
    
    val programDay = Transformations.switchMap(_programDayId) {
        when (it) {
            null -> programRepository.loadNextProgramDayR()
            else -> programRepository.loadProgramDayR(it)
        }
    }
    
    fun setProgramDay(id: Long) {
        _programDayId.setNewValue(id)
    }
    
    init {
        val locale = Locale.getDefault()
        date.value = SimpleDateFormat("yyyy-MM-dd", locale).format(calendar.time)
        time.value = SimpleDateFormat("HH:mm:ss", locale).format(calendar.time)

        byProgram.value = false
    }

    fun createTraining(callback: ItemInsertedListener) {
        trainingRepository.insertTraining(Training(0,"${date.value} ${time.value}", _programDayId.value), callback)
    }
}
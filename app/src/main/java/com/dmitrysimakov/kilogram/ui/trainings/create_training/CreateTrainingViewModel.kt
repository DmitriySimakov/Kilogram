package com.dmitrysimakov.kilogram.ui.trainings.create_training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.data.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.entity.Training
import com.dmitrysimakov.kilogram.data.relation.ProgramDayR
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import java.util.*
import javax.inject.Inject

class CreateTrainingViewModel @Inject constructor(
        private val trainingRepository: TrainingRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val programDayRepository: ProgramDayRepository
) : ViewModel() {
    
    private val _calendar = MutableLiveData<Calendar>(Calendar.getInstance())
    fun updateCalendar() { _calendar.value = _calendar.value }
    val calendar: LiveData<Calendar>
        get() = _calendar
    
    val byProgram = MutableLiveData(false)
    
    fun setProgramDay(id: Long) { _programDayId.setNewValue(id) }
    private val _programDayId = MutableLiveData<Long>()
    val programDay = Transformations.switchMap(_programDayId) {
        when (it) {
            0L -> programDayRepository.loadNextProgramDayR()
            else -> programDayRepository.loadProgramDayR(it)
        }
    }

    fun createTraining(callback: ItemInsertedListener) {
        val programDayId = if (byProgram.value!!) programDay.value?.program_day_id else null
        trainingRepository.insertTraining(Training(0,programDayId, calendar.value!!.timeInMillis), callback)
    }
    
    fun fillTrainingWithProgramExercises(trainingId: Long) {
        programDay.value?.let { trainingExerciseRepository.fillTrainingWithProgramExercises(trainingId, it.program_day_id) }
    }
}
package com.dmitrysimakov.kilogram.ui.trainings.create_training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.data.entity.Training
import com.dmitrysimakov.kilogram.data.entity.TrainingMuscle
import com.dmitrysimakov.kilogram.data.repository.*
import com.dmitrysimakov.kilogram.util.setNewValue
import java.util.*
import javax.inject.Inject

class CreateTrainingViewModel @Inject constructor(
        private val trainingRepo: TrainingRepository,
        private val trainingExerciseRepo: TrainingExerciseRepository,
        private val programDayRepo: ProgramDayRepository,
        private val exerciseRepo: ExerciseRepository,
        private val trainingMuscleRepo: TrainingMuscleRepository
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
            0L -> programDayRepo.loadNextProgramDayR()
            else -> programDayRepo.loadProgramDayR(it)
        }
    }
    
    fun createTraining(callback: ItemInsertedListener) {
        val programDayId = if (byProgram.value!!) programDay.value?.program_day_id else null
        trainingRepo.insertTraining(Training(0,programDayId, calendar.value!!.timeInMillis), callback)
    }
    
    fun fillTrainingWithProgramExercises(trainingId: Long) {
        programDay.value?.let { trainingExerciseRepo.fillTrainingWithProgramExercises(trainingId, it.program_day_id) }
    }
    
    
    val muscleList = exerciseRepo.loadMuscleParams()
    
    fun saveMuscles(trainingId: Long) {
        val list = mutableListOf<TrainingMuscle>()
        for (muscle in muscleList.value!!) {
            if (muscle.is_active) list.add(TrainingMuscle(trainingId, muscle._id))
        }
        trainingMuscleRepo.insert(list)
    }
}
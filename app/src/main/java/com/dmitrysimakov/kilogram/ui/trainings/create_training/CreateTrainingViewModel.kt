package com.dmitrysimakov.kilogram.ui.trainings.create_training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.local.dao.MuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.local.entity.TrainingMuscle
import com.dmitrysimakov.kilogram.data.relation.ProgramDayAndProgram
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingMuscleRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CreateTrainingViewModel(
        muscleDao: MuscleDao,
        private val trainingRepo: TrainingRepository,
        private val trainingExerciseRepo: TrainingExerciseRepository,
        private val programDayRepo: ProgramDayRepository,
        private val trainingMuscleRepo: TrainingMuscleRepository
) : ViewModel() {
    
    private val _calendar = MutableLiveData<Calendar>(Calendar.getInstance())
    val calendar: LiveData<Calendar> = _calendar
    fun updateCalendar() { _calendar.value = _calendar.value }
    
    val byProgram = MutableLiveData(false)
    
    fun setProgramDay(id: Long) { _chosenProgramDayId.setNewValue(id) }
    private val _chosenProgramDayId = MutableLiveData<Long>()
    val programDay = _chosenProgramDayId.switchMap {
        when (it) {
            0L -> programDayRepo.loadNextProgramDayAndProgram()
            else -> programDayRepo.loadProgramDayAndProgram(it)
        }
    }
    
    suspend fun createTraining(): Long {
        val programDayId = byProgram.value?.let { programDay.value?.program_day_id }
        return trainingRepo.insertTraining(
                Training(0, calendar.value!!.timeInMillis, null, programDayId))
    }
    
    fun fillTrainingWithProgramExercises(trainingId: Long) { CoroutineScope(Dispatchers.IO).launch {
        programDay.value?.program_day_id?.let {
            trainingExerciseRepo.fillTrainingWithProgramExercises(trainingId, it)
        }
    }}
    
    
    val muscleList = programDay.switchMap {
        when (it?.program_day_id) {
            null, 0L -> muscleDao.getParamList()
            else -> muscleDao.getProgramDayParamList(it.program_day_id)
        }
    }
    
    fun saveMuscles(trainingId: Long) { CoroutineScope(Dispatchers.IO).launch {
        val list = mutableListOf<TrainingMuscle>()
        for (muscle in muscleList.value!!) {
            if (muscle.is_active) list.add(TrainingMuscle(trainingId, muscle.name))
        }
        trainingMuscleRepo.insert(list)
    }}
}
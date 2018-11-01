package com.dmitrysimakov.kilogram.ui.createTraining

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.data.entity.Training
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CreateTrainingViewModel @Inject constructor(
        private val repository: TrainingRepository
) : ViewModel() {

    val calendar: Calendar = Calendar.getInstance()

    val byProgram = MutableLiveData<Boolean>()
    val date = MutableLiveData<String>()
    val time = MutableLiveData<String>()

    init {
        date.value = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
        time.value = SimpleDateFormat("HH:mm:ss").format(calendar.time)

        byProgram.value = false
    }

    fun createTraining(callback: ItemInsertedListener) {
        repository.insertTraining(Training("${date.value} ${time.value}"), callback)
    }
}
package com.dmitrysimakov.kilogram.ui.trainings.create_training

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
    val date = MutableLiveData<String>()
    val time = MutableLiveData<String>()

    val byProgram = MutableLiveData<Boolean>()

    init {
        val locale = Locale.getDefault()
        date.value = SimpleDateFormat("yyyy-MM-dd", locale).format(calendar.time)
        time.value = SimpleDateFormat("HH:mm:ss", locale).format(calendar.time)

        byProgram.value = false
    }

    fun createTraining(callback: ItemInsertedListener) {
        repository.insertTraining(Training(date_time = "${date.value} ${time.value}"), callback)
    }
}
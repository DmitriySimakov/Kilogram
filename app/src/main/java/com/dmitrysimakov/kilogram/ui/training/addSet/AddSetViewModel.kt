package com.dmitrysimakov.kilogram.ui.training.addSet

import androidx.lifecycle.ViewModel;
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import javax.inject.Inject

class AddSetViewModel @Inject constructor(
        private val repository: TrainingRepository
) : ViewModel() {
}

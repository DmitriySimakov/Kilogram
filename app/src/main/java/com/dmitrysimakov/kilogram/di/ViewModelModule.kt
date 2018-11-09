package com.dmitrysimakov.kilogram.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmitrysimakov.kilogram.ui.training.createTraining.CreateTrainingViewModel
import com.dmitrysimakov.kilogram.ui.exercises.chooseMuscle.ChooseMuscleViewModel
import com.dmitrysimakov.kilogram.ui.exercises.detail.ExerciseDetailViewModel
import com.dmitrysimakov.kilogram.ui.exercises.exercises.ExercisesViewModel
import com.dmitrysimakov.kilogram.ui.measurements.addMeasurement.MeasurementsViewModel
import com.dmitrysimakov.kilogram.ui.training.training.TrainingViewModel
import com.dmitrysimakov.kilogram.ui.training.trainingList.TrainingsViewModel
import com.dmitrysimakov.kilogram.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChooseMuscleViewModel::class)
    abstract fun bindChooseMuscleViewModel(viewModel: ChooseMuscleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExercisesViewModel::class)
    abstract fun bindExercisesViewModel(viewModel: ExercisesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseDetailViewModel::class)
    abstract fun bindExerciseDetailViewModel(viewModel: ExerciseDetailViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(TrainingsViewModel::class)
    abstract fun bindTrainingsViewModel(viewModel: TrainingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateTrainingViewModel::class)
    abstract fun bindCreateTrainingViewModel(viewModel: CreateTrainingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrainingViewModel::class)
    abstract fun bindTrainingViewModel(viewModel: TrainingViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MeasurementsViewModel::class)
    abstract fun bindMeasurementsViewModel(viewModel: MeasurementsViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
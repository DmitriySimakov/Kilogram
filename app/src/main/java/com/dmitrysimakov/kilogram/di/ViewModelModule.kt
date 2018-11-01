package com.dmitrysimakov.kilogram.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmitrysimakov.kilogram.ui.createTraining.CreateTrainingViewModel
import com.dmitrysimakov.kilogram.ui.exercises.ChooseMuscleViewModel
import com.dmitrysimakov.kilogram.ui.exercises.ExerciseDetailViewModel
import com.dmitrysimakov.kilogram.ui.exercises.ExercisesViewModel
import com.dmitrysimakov.kilogram.ui.training.TrainingViewModel
import com.dmitrysimakov.kilogram.ui.trainings.TrainingsViewModel
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
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
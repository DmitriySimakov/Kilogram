package com.dmitrysimakov.kilogram.di

import com.dmitrysimakov.kilogram.ui.createTraining.CreateTrainingDialog
import com.dmitrysimakov.kilogram.ui.exercises.ChooseMuscleFragment
import com.dmitrysimakov.kilogram.ui.exercises.ExerciseDetailFragment
import com.dmitrysimakov.kilogram.ui.exercises.ExercisesFragment
import com.dmitrysimakov.kilogram.ui.main.MainFragment
import com.dmitrysimakov.kilogram.ui.training.TrainingFragment
import com.dmitrysimakov.kilogram.ui.trainings.TrainingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment


    @ContributesAndroidInjector
    abstract fun contributeChooseMuscleFragment(): ChooseMuscleFragment

    @ContributesAndroidInjector
    abstract fun contributeExercisesFragment(): ExercisesFragment

    @ContributesAndroidInjector
    abstract fun contributeExerciseDetailFragment(): ExerciseDetailFragment


    @ContributesAndroidInjector
    abstract fun contributeTrainingsFragment(): TrainingsFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateTrainingDialog(): CreateTrainingDialog

    @ContributesAndroidInjector
    abstract fun contributeTrainingFragment(): TrainingFragment
}
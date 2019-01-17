package com.dmitrysimakov.kilogram.di

import com.dmitrysimakov.kilogram.ui.training.createTraining.CreateTrainingDialog
import com.dmitrysimakov.kilogram.ui.exercises.chooseMuscle.ChooseMuscleFragment
import com.dmitrysimakov.kilogram.ui.exercises.detail.ExerciseDetailFragment
import com.dmitrysimakov.kilogram.ui.exercises.exercises.ExercisesFragment
import com.dmitrysimakov.kilogram.ui.main.MainFragment
import com.dmitrysimakov.kilogram.ui.measurements.addMeasurement.MeasurementsFragment
import com.dmitrysimakov.kilogram.ui.training.addExercise.AddExerciseDialog
import com.dmitrysimakov.kilogram.ui.training.training.TrainingFragment
import com.dmitrysimakov.kilogram.ui.training.trainingList.TrainingsFragment
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

    @ContributesAndroidInjector
    abstract fun contributeAddExerciseDialog(): AddExerciseDialog


    @ContributesAndroidInjector
    abstract fun contributeMeasurementsFragment(): MeasurementsFragment
}
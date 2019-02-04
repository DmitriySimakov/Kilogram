package com.dmitrysimakov.kilogram.di

import com.dmitrysimakov.kilogram.ui.training.create_training.CreateTrainingDialog
import com.dmitrysimakov.kilogram.ui.exercises.choose_muscle.ChooseMuscleFragment
import com.dmitrysimakov.kilogram.ui.exercises.detail.ExerciseDetailFragment
import com.dmitrysimakov.kilogram.ui.exercises.exercises.ExercisesFragment
import com.dmitrysimakov.kilogram.ui.main.MainFragment
import com.dmitrysimakov.kilogram.ui.measurements.add_measurement.MeasurementsFragment
import com.dmitrysimakov.kilogram.ui.training.add_exercise.AddExerciseDialog
import com.dmitrysimakov.kilogram.ui.training.add_set.AddSetDialog
import com.dmitrysimakov.kilogram.ui.training.sets.TrainingSetsFragment
import com.dmitrysimakov.kilogram.ui.training.training.TrainingFragment
import com.dmitrysimakov.kilogram.ui.training.training_list.TrainingsFragment
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
    abstract fun contributeTrainingSetsFragment(): TrainingSetsFragment

    @ContributesAndroidInjector
    abstract fun contributeAddSetDialog(): AddSetDialog


    @ContributesAndroidInjector
    abstract fun contributeMeasurementsFragment(): MeasurementsFragment
}
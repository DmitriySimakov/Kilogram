package com.dmitrysimakov.kilogram.di

import com.dmitrysimakov.kilogram.ui.common.add_exercise.AddExerciseFragment
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment
import com.dmitrysimakov.kilogram.ui.common.choose_muscle.ChooseMuscleFragment
import com.dmitrysimakov.kilogram.ui.common.exercises.ExercisesFragment
import com.dmitrysimakov.kilogram.ui.exercises.detail.ExerciseDetailFragment
import com.dmitrysimakov.kilogram.ui.main.MainFragment
import com.dmitrysimakov.kilogram.ui.measurements.add_measurement.MeasurementsFragment
import com.dmitrysimakov.kilogram.ui.programs.create_program.CreateProgramDialog
import com.dmitrysimakov.kilogram.ui.programs.create_program_day.CreateProgramDayDialog
import com.dmitrysimakov.kilogram.ui.programs.program_days.ProgramDaysFragment
import com.dmitrysimakov.kilogram.ui.programs.programs.ProgramsFragment
import com.dmitrysimakov.kilogram.ui.trainings.add_set.AddSetDialog
import com.dmitrysimakov.kilogram.ui.trainings.create_training.CreateTrainingDialog
import com.dmitrysimakov.kilogram.ui.trainings.sets.TrainingSetsFragment
import com.dmitrysimakov.kilogram.ui.trainings.trainings.TrainingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    // COMMON
    
    @ContributesAndroidInjector
    abstract fun contributeChooseMuscleFragment(): ChooseMuscleFragment
    
    @ContributesAndroidInjector
    abstract fun contributeChooseExerciseFragment(): ChooseExerciseFragment
    
    @ContributesAndroidInjector
    abstract fun contributeAddExerciseFragment(): AddExerciseFragment
    
    @ContributesAndroidInjector
    abstract fun contributeExercisesFragment(): ExercisesFragment
    
    // EXERCISES

    @ContributesAndroidInjector
    abstract fun contributeExerciseDetailFragment(): ExerciseDetailFragment
    
    // PROGRAMS
    
    @ContributesAndroidInjector
    abstract fun contributeProgramsFragment(): ProgramsFragment
    
    @ContributesAndroidInjector
    abstract fun contributeCreateProgramDialog(): CreateProgramDialog
    
    @ContributesAndroidInjector
    abstract fun contributeProgramDaysFragment(): ProgramDaysFragment
    
    @ContributesAndroidInjector
    abstract fun contributeCreateProgramDayDialog(): CreateProgramDayDialog

    // TRAININGS
    
    @ContributesAndroidInjector
    abstract fun contributeTrainingsFragment(): TrainingsFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateTrainingDialog(): CreateTrainingDialog

    @ContributesAndroidInjector
    abstract fun contributeTrainingSetsFragment(): TrainingSetsFragment

    @ContributesAndroidInjector
    abstract fun contributeAddSetDialog(): AddSetDialog

    // MEASUREMENTS
    
    @ContributesAndroidInjector
    abstract fun contributeMeasurementsFragment(): MeasurementsFragment
}
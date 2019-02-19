package com.dmitrysimakov.kilogram.di

import com.dmitrysimakov.kilogram.ui.common.add_exercise.AddExerciseFragment
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment
import com.dmitrysimakov.kilogram.ui.common.choose_muscle.ChooseMuscleFragment
import com.dmitrysimakov.kilogram.ui.common.exercises.ExercisesFragment
import com.dmitrysimakov.kilogram.ui.exercises.choose_exercise.Exercises_ChooseExerciseFragment
import com.dmitrysimakov.kilogram.ui.exercises.choose_muscle.Exercises_ChooseMuscleFragment
import com.dmitrysimakov.kilogram.ui.exercises.detail.ExerciseDetailFragment
import com.dmitrysimakov.kilogram.ui.main.MainFragment
import com.dmitrysimakov.kilogram.ui.measurements.add_measurement.MeasurementsFragment
import com.dmitrysimakov.kilogram.ui.programs.add_exercise.Programs_AddExerciseFragment
import com.dmitrysimakov.kilogram.ui.programs.choose_exercise.Programs_ChooseExerciseFragment
import com.dmitrysimakov.kilogram.ui.programs.choose_muscle.Programs_ChooseMuscleFragment
import com.dmitrysimakov.kilogram.ui.programs.create_program.CreateProgramDialog
import com.dmitrysimakov.kilogram.ui.programs.create_program_day.CreateProgramDayDialog
import com.dmitrysimakov.kilogram.ui.programs.exercises.Programs_ExercisesFragment
import com.dmitrysimakov.kilogram.ui.programs.program_days.ProgramDaysFragment
import com.dmitrysimakov.kilogram.ui.programs.programs.ProgramsFragment
import com.dmitrysimakov.kilogram.ui.trainings.add_exercise.Trainings_AddExerciseFragment
import com.dmitrysimakov.kilogram.ui.trainings.add_set.AddSetDialog
import com.dmitrysimakov.kilogram.ui.trainings.choose_exercise.Trainings_ChooseExerciseFragment
import com.dmitrysimakov.kilogram.ui.trainings.choose_muscle.Trainings_ChooseMuscleFragment
import com.dmitrysimakov.kilogram.ui.trainings.create_training.CreateTrainingDialog
import com.dmitrysimakov.kilogram.ui.trainings.exercises.Trainings_ExercisesFragment
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
    abstract fun contributeExercises_ChooseMuscleFragment(): Exercises_ChooseMuscleFragment
    
    @ContributesAndroidInjector
    abstract fun contributeExercises_ChooseExerciseFragment(): Exercises_ChooseExerciseFragment
    
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
    
    @ContributesAndroidInjector
    abstract fun contributePrograms_ExercisesFragment(): Programs_ExercisesFragment
    
    @ContributesAndroidInjector
    abstract fun contributePrograms_ChooseMuscleFragment(): Programs_ChooseMuscleFragment
    
    @ContributesAndroidInjector
    abstract fun contributePrograms_ChooseExerciseFragment(): Programs_ChooseExerciseFragment
    
    @ContributesAndroidInjector
    abstract fun contributePrograms_AddExerciseFragment(): Programs_AddExerciseFragment

    // TRAININGS
    
    @ContributesAndroidInjector
    abstract fun contributeTrainingsFragment(): TrainingsFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateTrainingDialog(): CreateTrainingDialog
    
    @ContributesAndroidInjector
    abstract fun contributeTrainings_ExercisesFragment(): Trainings_ExercisesFragment
    
    @ContributesAndroidInjector
    abstract fun contributeTrainingSetsFragment(): TrainingSetsFragment

    @ContributesAndroidInjector
    abstract fun contributeAddSetDialog(): AddSetDialog
    
    @ContributesAndroidInjector
    abstract fun contributeTrainings_ChooseMuscleFragment(): Trainings_ChooseMuscleFragment
    
    @ContributesAndroidInjector
    abstract fun contributeTrainings_ChooseExerciseFragment(): Trainings_ChooseExerciseFragment
    
    @ContributesAndroidInjector
    abstract fun contributeTrainings_AddExerciseFragment(): Trainings_AddExerciseFragment

    // MEASUREMENTS
    
    @ContributesAndroidInjector
    abstract fun contributeMeasurementsFragment(): MeasurementsFragment
}
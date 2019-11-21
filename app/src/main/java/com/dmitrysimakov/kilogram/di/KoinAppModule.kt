package com.dmitrysimakov.kilogram.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.dmitrysimakov.kilogram.data.local.KilogramDb
import com.dmitrysimakov.kilogram.data.repository.*
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.catalog.exercises.detail.DetailedExerciseViewModel
import com.dmitrysimakov.kilogram.ui.catalog.exercises.exercise_targets.ExerciseTargetsViewModel
import com.dmitrysimakov.kilogram.ui.catalog.programs.create_program.CreateProgramViewModel
import com.dmitrysimakov.kilogram.ui.catalog.programs.create_program_day.CreateProgramDayViewModel
import com.dmitrysimakov.kilogram.ui.catalog.programs.exercises.ProgramDayExercisesViewModel
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseViewModel
import com.dmitrysimakov.kilogram.ui.common.choose_program.ChooseProgramViewModel
import com.dmitrysimakov.kilogram.ui.common.choose_program_day.ChooseProgramDayViewModel
import com.dmitrysimakov.kilogram.ui.home.measurements.add_measurement.MeasurementsViewModel
import com.dmitrysimakov.kilogram.ui.home.trainings.add_training_set.AddTrainingSetViewModel
import com.dmitrysimakov.kilogram.ui.home.trainings.create_training.CreateTrainingViewModel
import com.dmitrysimakov.kilogram.ui.home.trainings.exercises.TrainingExercisesViewModel
import com.dmitrysimakov.kilogram.ui.home.trainings.training_sets.TrainingSetsViewModel
import com.dmitrysimakov.kilogram.ui.home.trainings.trainings.TrainingsViewModel
import com.dmitrysimakov.kilogram.ui.subscriptions.messages.ChatsViewModel
import com.dmitrysimakov.kilogram.ui.subscriptions.messages.MessagesViewModel
import com.dmitrysimakov.kilogram.ui.subscriptions.people.PeopleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideSharedPreferences(androidContext()) }
    // Database
    single { provideDb(androidContext()) }
    // Data Access Objects
    single { get<KilogramDb>().exerciseDao() }
    single { get<KilogramDb>().equipmentDao() }
    single { get<KilogramDb>().programDao() }
    single { get<KilogramDb>().programDayDao() }
    single { get<KilogramDb>().programDayTargetDao() }
    single { get<KilogramDb>().programDayExerciseDao() }
    single { get<KilogramDb>().exerciseTargetDao() }
    single { get<KilogramDb>().targetedMuscleDao() }
    single { get<KilogramDb>().trainingDao() }
    single { get<KilogramDb>().trainingMuscleDao() }
    single { get<KilogramDb>().trainingExerciseDao() }
    single { get<KilogramDb>().trainingExerciseSetDao() }
    single { get<KilogramDb>().measurementDao() }
    // Repositories
    single { ExerciseRepository(get()) }
    single { MeasurementRepository(get()) }
    single { ProgramDayRepository(get()) }
    single { ProgramDayExerciseRepository(get()) }
    single { ProgramDayTargetsRepository(get()) }
    single { ProgramRepository(get()) }
    single { TrainingExerciseRepository(get()) }
    single { TrainingSetRepository(get()) }
    single { TrainingMuscleRepository(get()) }
    single { TrainingRepository(get()) }
    // ViewModels
    viewModel { SharedViewModel(get()) }
    viewModel { ExerciseTargetsViewModel(get()) }
    viewModel { ChooseExerciseViewModel(get(), get(), get(), get(), get(), get(), get()) }
    viewModel { ChooseProgramViewModel(get()) }
    viewModel { ChooseProgramDayViewModel(get()) }
    viewModel { DetailedExerciseViewModel(get(), get()) }
    viewModel { CreateProgramViewModel(get()) }
    viewModel { CreateProgramDayViewModel(get(), get(), get()) }
    viewModel { ProgramDayExercisesViewModel(get()) }
    viewModel { TrainingsViewModel(get()) }
    viewModel { CreateTrainingViewModel(get(), get(), get(), get(), get()) }
    viewModel { TrainingExercisesViewModel(get(), get(), get()) }
    viewModel { TrainingSetsViewModel(get(), get(), get()) }
    viewModel { AddTrainingSetViewModel(get(), get(), get()) }
    viewModel { MeasurementsViewModel(get()) }
    viewModel { ChatsViewModel() }
    viewModel { MessagesViewModel() }
    viewModel { PeopleViewModel() }
}

fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
}

fun provideDb(context: Context): KilogramDb {
    return Room.databaseBuilder(context, KilogramDb::class.java, "kilogram.db")
            .createFromAsset("kilogram.db")
            .fallbackToDestructiveMigration()
            .build()
}

package com.dmitrysimakov.kilogram.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.KilogramApp
import com.dmitrysimakov.kilogram.data.local.KilogramDb
import com.dmitrysimakov.kilogram.data.repository.*
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.common.add_exercise.AddExerciseViewModel
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseViewModel
import com.dmitrysimakov.kilogram.ui.common.choose_program.ChooseProgramViewModel
import com.dmitrysimakov.kilogram.ui.common.choose_program_day.ChooseProgramDayViewModel
import com.dmitrysimakov.kilogram.ui.exercises.choose_muscle.ChooseMuscleViewModel
import com.dmitrysimakov.kilogram.ui.exercises.detail.ExerciseDetailViewModel
import com.dmitrysimakov.kilogram.ui.measurements.add_measurement.MeasurementsViewModel
import com.dmitrysimakov.kilogram.ui.messages.ChatsViewModel
import com.dmitrysimakov.kilogram.ui.messages.MessagesViewModel
import com.dmitrysimakov.kilogram.ui.people.PeopleViewModel
import com.dmitrysimakov.kilogram.ui.programs.create_program.CreateProgramViewModel
import com.dmitrysimakov.kilogram.ui.programs.create_program_day.CreateProgramDayViewModel
import com.dmitrysimakov.kilogram.ui.programs.exercises.ProgramDayExercisesViewModel
import com.dmitrysimakov.kilogram.ui.trainings.add_set.AddSetViewModel
import com.dmitrysimakov.kilogram.ui.trainings.create_training.CreateTrainingViewModel
import com.dmitrysimakov.kilogram.ui.trainings.exercises.TrainingExercisesViewModel
import com.dmitrysimakov.kilogram.ui.trainings.sets.TrainingSetsViewModel
import com.dmitrysimakov.kilogram.ui.trainings.trainings.TrainingsViewModel
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.worker.SeedDatabaseWorker
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppExecutors() }
    single { provideSharedPreferences(androidApplication()) }
    // Database
    single { provideDb(androidApplication()) }
    // Data Access Objects
    single { get<KilogramDb>().exerciseDao() }
    single { get<KilogramDb>().mechanicsTypeDao() }
    single { get<KilogramDb>().exerciseTypeDao() }
    single { get<KilogramDb>().equipmentDao() }
    single { get<KilogramDb>().programDao() }
    single { get<KilogramDb>().programDayDao() }
    single { get<KilogramDb>().programDayMuscleDao() }
    single { get<KilogramDb>().programDayExerciseDao() }
    single { get<KilogramDb>().muscleDao() }
    single { get<KilogramDb>().trainingDao() }
    single { get<KilogramDb>().trainingMuscleDao() }
    single { get<KilogramDb>().trainingExerciseDao() }
    single { get<KilogramDb>().trainingExerciseSetDao() }
    single { get<KilogramDb>().measurementDao() }
    // Repositories
    single { ExerciseRepository(get(), get(), get(), get(), get(), get()) }
    single { MeasurementRepository(get()) }
    single { MuscleRepository(get()) }
    single { ProgramDayExerciseRepository(get(), get()) }
    single { ProgramDayMuscleRepository(get(), get()) }
    single { ProgramRepository(get(), get()) }
    single { TrainingExerciseRepository(get(), get()) }
    single { TrainingExerciseSetRepository(get(), get()) }
    single { TrainingMuscleRepository(get(), get()) }
    single { TrainingRepository(get(), get()) }
    // ViewModels
    viewModel { SharedViewModel(get()) }
    viewModel { ChooseMuscleViewModel(get()) }
    viewModel { ChooseExerciseViewModel(get(), get(), get()) }
    viewModel { AddExerciseViewModel(get(), get(), get()) }
    viewModel { ChooseProgramViewModel(get()) }
    viewModel { ChooseProgramDayViewModel(get()) }
    viewModel { ExerciseDetailViewModel(get()) }
    viewModel { CreateProgramViewModel(get()) }
    viewModel { CreateProgramDayViewModel(get(), get(), get()) }
    viewModel { ProgramDayExercisesViewModel(get()) }
    viewModel { TrainingsViewModel(get()) }
    viewModel { CreateTrainingViewModel(get(), get(), get(), get(), get()) }
    viewModel { TrainingExercisesViewModel(get(), get(), get()) }
    viewModel { TrainingSetsViewModel(get(), get(), get()) }
    viewModel { AddSetViewModel(get(), get()) }
    viewModel { MeasurementsViewModel(get()) }
    viewModel { ChatsViewModel() }
    viewModel { MessagesViewModel() }
    viewModel { PeopleViewModel() }
}

fun provideSharedPreferences(app: Application): SharedPreferences {
    return app.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
}

fun provideDb(app: Application): KilogramDb {
    return Room.databaseBuilder(app, KilogramDb::class.java, "kilogram.db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                    WorkManager.getInstance().enqueue(request)
                }
            })
            .fallbackToDestructiveMigration()
            .build()
}

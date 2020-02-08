package com.dmitrysimakov.kilogram.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.data.local.KilogramDb
import com.dmitrysimakov.kilogram.data.remote.data_sources.*
import com.dmitrysimakov.kilogram.data.repository.*
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.common.choose_program.ChooseProgramViewModel
import com.dmitrysimakov.kilogram.ui.common.exercises.ExercisesViewModel
import com.dmitrysimakov.kilogram.ui.common.messages.MessagesViewModel
import com.dmitrysimakov.kilogram.ui.create_post.CreatePostViewModel
import com.dmitrysimakov.kilogram.ui.exercises.DetailedExerciseViewModel
import com.dmitrysimakov.kilogram.ui.feed.FeedViewModel
import com.dmitrysimakov.kilogram.ui.home.HomeViewModel
import com.dmitrysimakov.kilogram.ui.home.calendar_day.CalendarDayViewModel
import com.dmitrysimakov.kilogram.ui.home.measurements.MeasurementsViewModel
import com.dmitrysimakov.kilogram.ui.home.measurements.add_measurement.AddMeasurementViewModel
import com.dmitrysimakov.kilogram.ui.home.measurements.measurements_history.MeasurementsHistoryViewModel
import com.dmitrysimakov.kilogram.ui.home.measurements.proportions_calculator.ProportionsCalculatorViewModel
import com.dmitrysimakov.kilogram.ui.home.photos.PhotosViewModel
import com.dmitrysimakov.kilogram.ui.home.photos.photo.PhotoViewModel
import com.dmitrysimakov.kilogram.ui.home.programs.create_program.CreateProgramViewModel
import com.dmitrysimakov.kilogram.ui.home.programs.create_program_day.CreateProgramDayViewModel
import com.dmitrysimakov.kilogram.ui.home.programs.exercises.ProgramDayExercisesViewModel
import com.dmitrysimakov.kilogram.ui.home.programs.program_days.ProgramDaysViewModel
import com.dmitrysimakov.kilogram.ui.home.trainings.add_training_set.AddTrainingSetViewModel
import com.dmitrysimakov.kilogram.ui.home.trainings.create_training.CreateTrainingViewModel
import com.dmitrysimakov.kilogram.ui.home.trainings.exercises.TrainingExercisesViewModel
import com.dmitrysimakov.kilogram.ui.home.trainings.training_sets.TrainingSetsViewModel
import com.dmitrysimakov.kilogram.ui.person_page.PersonPageViewModel
import com.dmitrysimakov.kilogram.ui.person_page.subscribers.SubscribersViewModel
import com.dmitrysimakov.kilogram.ui.person_page.subscriptions.SubscriptionsViewModel
import com.dmitrysimakov.kilogram.ui.profile.chats.ChatsViewModel
import com.dmitrysimakov.kilogram.ui.profile.edit_profile.EditProfileViewModel
import com.dmitrysimakov.kilogram.ui.search.people.PeopleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { androidContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE) }
    single { WorkManager.getInstance(androidContext()) }
    // Database
    single { provideDb(androidContext()) }
    // Data Access Objects
    single { get<KilogramDb>().exerciseDao() }
    single { get<KilogramDb>().equipmentDao() }
    single { get<KilogramDb>().photoDao() }
    single { get<KilogramDb>().programDao() }
    single { get<KilogramDb>().programDayDao() }
    single { get<KilogramDb>().programDayExerciseDao() }
    single { get<KilogramDb>().exerciseTargetDao() }
    single { get<KilogramDb>().targetedMuscleDao() }
    single { get<KilogramDb>().trainingDao() }
    single { get<KilogramDb>().trainingExerciseDao() }
    single { get<KilogramDb>().trainingSetDao() }
    single { get<KilogramDb>().measurementDao() }
    single { get<KilogramDb>().measurementParamDao() }
    // Remote data sources
    single { FirebaseStorageSource() }
    single { MeasurementSource(get()) }
    single { MessageSource() }
    single { PhotoSource(get()) }
    single { PostSource() }
    single { ProgramSource(get()) }
    single { TrainingSource(get()) }
    // Repositories
    single { ExerciseRepository(get()) }
    single { MeasurementRepository(get(), get()) }
    single { PhotoRepository(get(), get()) }
    single { ProgramDayRepository(get(), get()) }
    single { ProgramDayExerciseRepository(get(), get()) }
    single { ProgramRepository(get(), get(), get(), get()) }
    single { TrainingExerciseRepository(get(), get()) }
    single { TrainingSetRepository(get(), get()) }
    single { TrainingRepository(get(), get()) }
    // ViewModels
    viewModel { SharedViewModel(get(), get()) }
    viewModel { AddMeasurementViewModel(get(), get()) }
    viewModel { AddTrainingSetViewModel(get(), get()) }
    viewModel { CalendarDayViewModel(get()) }
    viewModel { ChatsViewModel(get()) }
    viewModel { ExercisesViewModel(get(), get(), get(), get(), get()) }
    viewModel { ChooseProgramViewModel(get()) }
    viewModel { ProgramDaysViewModel(get()) }
    viewModel { CreatePostViewModel(get(), get(), get()) }
    viewModel { CreateProgramViewModel(get()) }
    viewModel { CreateProgramDayViewModel(get()) }
    viewModel { CreateTrainingViewModel(get(), get(), get()) }
    viewModel { DetailedExerciseViewModel(get(), get()) }
    viewModel { EditProfileViewModel(get()) }
    viewModel { SubscribersViewModel() }
    viewModel { SubscriptionsViewModel() }
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { MeasurementsViewModel(get()) }
    viewModel { MeasurementsHistoryViewModel(get()) }
    viewModel { MessagesViewModel(get(), get()) }
    viewModel { ProgramDayExercisesViewModel(get()) }
    viewModel { TrainingExercisesViewModel(get(), get(), get()) }
    viewModel { TrainingSetsViewModel(get(), get(), get()) }
    viewModel { PeopleViewModel() }
    viewModel { PersonPageViewModel() }
    viewModel { PhotoViewModel(get()) }
    viewModel { PhotosViewModel(get()) }
    viewModel { ProportionsCalculatorViewModel(get()) }
    viewModel { FeedViewModel(get()) }
}

fun provideDb(context: Context): KilogramDb {
    return Room.databaseBuilder(context, KilogramDb::class.java, "kilogram.db")
            .createFromAsset("kilogram.db")
            .fallbackToDestructiveMigration()
            .build()
}

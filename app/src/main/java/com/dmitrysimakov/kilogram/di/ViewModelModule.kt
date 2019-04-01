package com.dmitrysimakov.kilogram.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmitrysimakov.kilogram.ui.MainViewModel
import com.dmitrysimakov.kilogram.ui.common.add_exercise.AddExerciseViewModel
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseViewModel
import com.dmitrysimakov.kilogram.ui.exercises.choose_muscle.ChooseMuscleViewModel
import com.dmitrysimakov.kilogram.ui.exercises.detail.ExerciseDetailViewModel
import com.dmitrysimakov.kilogram.ui.measurements.add_measurement.MeasurementsViewModel
import com.dmitrysimakov.kilogram.ui.programs.create_program.CreateProgramViewModel
import com.dmitrysimakov.kilogram.ui.programs.create_program_day.CreateProgramDayViewModel
import com.dmitrysimakov.kilogram.ui.programs.exercises.ProgramDayExercisesViewModel
import com.dmitrysimakov.kilogram.ui.common.choose_program_day.ChooseProgramDayViewModel
import com.dmitrysimakov.kilogram.ui.common.choose_program.ChooseProgramViewModel
import com.dmitrysimakov.kilogram.ui.trainings.add_set.AddSetViewModel
import com.dmitrysimakov.kilogram.ui.trainings.create_training.CreateTrainingViewModel
import com.dmitrysimakov.kilogram.ui.trainings.exercises.TrainingExercisesViewModel
import com.dmitrysimakov.kilogram.ui.trainings.sets.TrainingSetsViewModel
import com.dmitrysimakov.kilogram.ui.trainings.trainings.TrainingsViewModel
import com.dmitrysimakov.kilogram.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
    
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
    
    //region COMMON
    
    @Binds
    @IntoMap
    @ViewModelKey(ChooseMuscleViewModel::class)
    abstract fun bindChooseMuscleViewModel(viewModel: ChooseMuscleViewModel): ViewModel
    
    @Binds
    @IntoMap
    @ViewModelKey(ChooseExerciseViewModel::class)
    abstract fun bindChooseExerciseViewModel(viewModel: ChooseExerciseViewModel): ViewModel
    
    @Binds
    @IntoMap
    @ViewModelKey(AddExerciseViewModel::class)
    abstract fun bindAddExerciseViewModel(viewModel: AddExerciseViewModel): ViewModel
    
    @Binds
    @IntoMap
    @ViewModelKey(ChooseProgramViewModel::class)
    abstract fun bindChooseProgramViewModel(viewModel: ChooseProgramViewModel): ViewModel
    
    @Binds
    @IntoMap
    @ViewModelKey(ChooseProgramDayViewModel::class)
    abstract fun bindChooseProgramDayViewModel(viewModel: ChooseProgramDayViewModel): ViewModel
    
    //endregion
    
    //region EXERCISES

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseDetailViewModel::class)
    abstract fun bindExerciseDetailViewModel(viewModel: ExerciseDetailViewModel): ViewModel
    
    //endregion
    
    //region PROGRAMS
    
    @Binds
    @IntoMap
    @ViewModelKey(CreateProgramViewModel::class)
    abstract fun bindCreateProgramViewModel(viewModel: CreateProgramViewModel): ViewModel
    
    @Binds
    @IntoMap
    @ViewModelKey(CreateProgramDayViewModel::class)
    abstract fun bindCreateProgramDayViewModel(viewModel: CreateProgramDayViewModel): ViewModel
    
    @Binds
    @IntoMap
    @ViewModelKey(ProgramDayExercisesViewModel::class)
    abstract fun bindProgramDayExercisesViewModel(viewModel: ProgramDayExercisesViewModel): ViewModel
    
    //endregion
    
    //region TRAININGS
    
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
    @ViewModelKey(TrainingExercisesViewModel::class)
    abstract fun bindTrainingExercisesViewModel(viewModel: TrainingExercisesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrainingSetsViewModel::class)
    abstract fun bindTrainingSetsViewModel(viewModel: TrainingSetsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddSetViewModel::class)
    abstract fun bindAddSetViewModel(viewModel: AddSetViewModel): ViewModel
    
    //endregion
    
    //region MEASUREMENTS

    @Binds
    @IntoMap
    @ViewModelKey(MeasurementsViewModel::class)
    abstract fun bindMeasurementsViewModel(viewModel: MeasurementsViewModel): ViewModel
    
    //endregion
}
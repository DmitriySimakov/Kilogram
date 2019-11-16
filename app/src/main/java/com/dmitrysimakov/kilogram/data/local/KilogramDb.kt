package com.dmitrysimakov.kilogram.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dmitrysimakov.kilogram.data.local.dao.*
import com.dmitrysimakov.kilogram.data.local.entity.*

@Database(
        entities = [
            Equipment::class,
            Exercise::class,
            ExerciseType::class,
            Measurement::class,
            MeasurementParam::class,
            MechanicsType::class,
            Muscle::class,
            Program::class,
            ProgramDay::class,
            ProgramDayMuscle::class,
            ProgramDayExercise::class,
            TargetedMuscle::class,
            Training::class,
            TrainingMuscle::class,
            TrainingExercise::class,
            TrainingSet::class
        ],
        version = 1,
        exportSchema = false
)
abstract class KilogramDb : RoomDatabase() {

    abstract fun equipmentDao(): EquipmentDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun exerciseTypeDao(): ExerciseTypeDao
    abstract fun measurementDao(): MeasurementDao
    abstract fun measurementParamDao(): MeasurementParamDao
    abstract fun mechanicsTypeDao(): MechanicsTypeDao
    abstract fun muscleDao(): MuscleDao
    abstract fun programDao(): ProgramDao
    abstract fun programDayDao(): ProgramDayDao
    abstract fun programDayMuscleDao(): ProgramDayMuscleDao
    abstract fun programDayExerciseDao(): ProgramDayExerciseDao
    abstract fun targetedMuscleDao(): TargetedMuscleDao
    abstract fun trainingDao(): TrainingDao
    abstract fun trainingMuscleDao(): TrainingMuscleDao
    abstract fun trainingExerciseDao(): TrainingExerciseDao
    abstract fun trainingExerciseSetDao(): TrainingExerciseSetDao
}
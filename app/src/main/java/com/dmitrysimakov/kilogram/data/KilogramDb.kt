package com.dmitrysimakov.kilogram.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dmitrysimakov.kilogram.data.dao.*
import com.dmitrysimakov.kilogram.data.entity.*

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
            ProgramDayExercise::class,
            TargetedMuscle::class,
            Training::class,
            TrainingExercise::class,
            TrainingExerciseSet::class
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
    abstract fun programDayExerciseDao(): ProgramDayExerciseDao
    abstract fun targetedMuscleDao(): TargetedMuscleDao
    abstract fun trainingDao(): TrainingDao
    abstract fun trainingExerciseDao(): TrainingExerciseDao
    abstract fun trainingExerciseSetDao(): TrainingExerciseSetDao
}
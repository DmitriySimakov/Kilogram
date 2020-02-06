package com.dmitrysimakov.kilogram.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dmitrysimakov.kilogram.data.local.dao.*
import com.dmitrysimakov.kilogram.data.model.*

@Database(
        entities = [
            Equipment::class,
            Exercise::class,
            Measurement::class,
            MeasurementParam::class,
            ExerciseTarget::class,
            Photo::class,
            Program::class,
            ProgramDay::class,
            ProgramDayExercise::class,
            TargetedMuscle::class,
            Training::class,
            TrainingExercise::class,
            TrainingSet::class
        ],
        version = 1,
        exportSchema = false
)
@TypeConverters(Converters::class)
abstract class KilogramDb : RoomDatabase() {

    abstract fun equipmentDao(): EquipmentDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun measurementDao(): MeasurementDao
    abstract fun measurementParamDao(): MeasurementParamDao
    abstract fun exerciseTargetDao(): ExerciseTargetDao
    abstract fun photoDao(): PhotoDao
    abstract fun programDao(): ProgramDao
    abstract fun programDayDao(): ProgramDayDao
    abstract fun programDayExerciseDao(): ProgramDayExerciseDao
    abstract fun targetedMuscleDao(): TargetedMuscleDao
    abstract fun trainingDao(): TrainingDao
    abstract fun trainingExerciseDao(): TrainingExerciseDao
    abstract fun trainingSetDao(): TrainingSetDao
}
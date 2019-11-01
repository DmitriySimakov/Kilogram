package com.dmitrysimakov.kilogram.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.dmitrysimakov.kilogram.data.local.KilogramDb
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class DbTest {
    
    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()
    
    private lateinit var _db: KilogramDb
    val db: KilogramDb
        get() = _db
    
    @Before fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        _db = Room.inMemoryDatabaseBuilder(context, KilogramDb::class.java).build()
    
        db.mechanicsTypeDao().insert(testMechanicsTypes)
        db.exerciseTypeDao().insert(testExerciseTypes)
        db.equipmentDao().insert(testEquipment)
        db.muscleDao().insert(testMuscles)
        
        db.exerciseDao().insert(testExercises)
        db.targetedMuscleDao().insert(testTargetedMuscles)
    
        db.programDao().insert(testPrograms)
        db.programDayDao().insert(testProgramDays)
        db.programDayMuscleDao().insert(testProgramDayMuscles)
        db.programDayExerciseDao().insert(testProgramDayExercises)
    
        db.trainingDao().insert(testTrainings)
        db.trainingMuscleDao().insert(testTrainingMuscles)
        db.trainingExerciseDao().insert(testTrainingExercises)
    }
    
    @After fun closeDb() {
        _db.close()
    }
}
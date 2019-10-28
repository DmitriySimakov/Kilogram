package com.dmitrysimakov.kilogram.data.local

import android.util.Log
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseInfo
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.util.getValue
import com.dmitrysimakov.kilogram.util.testExercises
import com.dmitrysimakov.kilogram.util.testProgramDayExercises
import com.dmitrysimakov.kilogram.util.testTrainingExercises
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test

class TrainingExerciseDaoTest : DbTest() {
    
    private lateinit var dao: TrainingExerciseDao
    
    @Before fun initDb() {
        dao = db.trainingExerciseDao()
    }
    
    @Test fun getTrainingExerciseRs() {
        val list = getValue(dao.getTrainingExerciseRs(1))
        
        val exercisesSample = testTrainingExercises
                .filter { it.training_id == 1L }
                .map { it.relation() }
                .sortedBy { it.num }
        
        assertThat(list.size, equalTo(exercisesSample.size))
        
        exercisesSample.forEachIndexed { i, trainingExerciseR ->
            assertThat(list[i], equalTo(trainingExerciseR))
        }
    }
    
    @Test fun getTrainingExerciseR() {
        val exercise = getValue(dao.getTrainingExerciseR(1))
        
        val exerciseSample = testTrainingExercises
                .find { it._id == 1L }!!
                .relation()
        assertThat(exercise, equalTo(exerciseSample))
    }
    
    @Test fun getPrevTrainingExerciseInfo() {
        val exerciseInfo = getValue(dao.getPrevTrainingExerciseInfo(2, 1))
        
        val exerciseInfoSample = TrainingExerciseInfo(1, 1, 0)
        
        assertThat(exerciseInfo, equalTo(exerciseInfoSample))
    }
    
    @Test fun fillTrainingWithProgramExercises() {
        dao.fillTrainingWithProgramExercises(3, 1)
        val exercises = getValue(dao.getTrainingExercises(3))
        val exercisesToFill = testProgramDayExercises
                .filter { it.program_day_id == 1L }
                .sortedBy { it.num }
        assertThat(exercises.size, equalTo(exercisesToFill.size))
        exercises.forEachIndexed { i, exercise ->
            assertThat(exercise.exercise_id, equalTo(exercisesToFill[i].exercise_id))
        }
    }
    
    fun TrainingExercise.relation() : TrainingExerciseR {
        val name = testExercises.find { it._id == exercise_id }!!.name
        return TrainingExerciseR(_id, exercise_id, name, num, rest, 0)
    }
}
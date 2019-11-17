package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.asLiveData
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseInfo
import com.dmitrysimakov.kilogram.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TrainingExerciseDaoTest : DbTest() {
    
    private lateinit var dao: TrainingExerciseDao
    
    @Before fun initDb() {
        dao = db.trainingExerciseDao()
    }
    
    @Test fun getDetailedTrainingExerciseList() = runBlockingTest {
        val list = getValue(dao.detailedTrainingExercisesFlow(1).asLiveData())
        
        val exercisesSample = testTrainingExercises
                .filter { it.training_id == 1L }
                .map { DetailedTrainingExercise(it._id, it.exercise, it.indexNumber, it.rest, 0) }
                .sortedBy { it.indexNumber }
        
        assertThat(list.size, equalTo(exercisesSample.size))
        
        exercisesSample.forEachIndexed { i, detailedTrainingExercise ->
            assertThat(list[i], equalTo(detailedTrainingExercise))
        }
    }
    
    @Test fun getPrevTrainingExercise() = runBlockingTest {
        val exerciseInfo = dao.previousTrainingExercise(2, testExercises[0].name)
        
        val exerciseInfoSample = TrainingExerciseInfo(1, 1, 0)
        
        assertThat(exerciseInfo, equalTo(exerciseInfoSample))
    }
    
    @Test fun fillTrainingWithProgramExercises()  = runBlockingTest {
        dao.fillTrainingWithProgramExercises(3, 1)
        val exercises = dao.trainingExercises(3)
        val exercisesToFill = testProgramDayExercises
                .filter { it.program_day_id == 1L }
                .sortedBy { it.indexNumber }
        assertThat(exercises.size, equalTo(exercisesToFill.size))
        exercises.forEachIndexed { i, exercise ->
            assertThat(exercise.exercise, equalTo(exercisesToFill[i].exercise))
        }
    }
}
package com.dmitrysimakov.kilogram.data.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.relation.ProgramExerciseR
import com.dmitrysimakov.kilogram.util.getValue
import com.dmitrysimakov.kilogram.util.testExercises
import com.dmitrysimakov.kilogram.util.testProgramDayExercises
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test

class ProgramDayExerciseDaoTest : DbTest() {
    
    private lateinit var dao: ProgramDayExerciseDao
    
    @Before fun initDb() {
        dao = db.programDayExerciseDao()
    }
    
    @Test fun getExercises() {
        val exercises = getValue(dao.getExercises(1))
        assertThat(exercises.size, equalTo(testProgramDayExercises.size))
    
        // Ensure list is sorted by num
        val sortedProgramDayExercises = testProgramDayExercises.sortedBy { it.num }
        for (i in 0..sortedProgramDayExercises.lastIndex) {
            val testExercise = sortedProgramDayExercises[i]
            val name = testExercises.find { it._id == testExercise._id }!!.name
            val sample = ProgramExerciseR(
                    testExercise._id,
                    testExercise.exercise_id,
                    name,
                    testExercise.num,
                    testExercise.rest,
                    testExercise.strategy
            )
            assertThat(exercises[i], equalTo(sample))
        }
    }
}
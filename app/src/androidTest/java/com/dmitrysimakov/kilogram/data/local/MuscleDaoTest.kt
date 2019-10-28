package com.dmitrysimakov.kilogram.data.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.data.local.dao.MuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.Program
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayMuscle
import com.dmitrysimakov.kilogram.data.relation.FilterParam
import com.dmitrysimakov.kilogram.util.getValue
import com.dmitrysimakov.kilogram.util.testMuscles
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test

class MuscleDaoTest : DbTest() {
    
    private lateinit var dao: MuscleDao
    
    @Before fun initDb() {
        dao = db.muscleDao()
    }
    
    @Test fun getParams() {
        val params = getValue(dao.getParams())
        assertThat(params.size, equalTo(testMuscles.size))
        
        // Ensure list is sorted by id
        for (i in 0..testMuscles.lastIndex) {
            assertThat(params[i], equalTo(FilterParam(testMuscles[i]._id, testMuscles[i].name, false)))
        }
    }
    
    @Test fun getProgramDayParams() {
        val programId = db.programDao().insert(Program(0, "PPL"))
        val programDayId = db.programDayDao().insert(ProgramDay(0, programId, 3, "Push"))
        db.programDayMuscleDao().insert(listOf(
                ProgramDayMuscle(programDayId, 3),
                ProgramDayMuscle(programDayId, 1)
        ))
        
        val params = getValue(dao.getProgramDayParams(programDayId))
        assertThat(params.size, equalTo(testMuscles.size))
    
        // Ensure list is sorted by id
        assertThat(params[0], equalTo(FilterParam(1, testMuscles[0].name, true)))
        assertThat(params[1], equalTo(FilterParam(2, testMuscles[1].name, false)))
        assertThat(params[2], equalTo(FilterParam(3, testMuscles[2].name, true)))
    }
}
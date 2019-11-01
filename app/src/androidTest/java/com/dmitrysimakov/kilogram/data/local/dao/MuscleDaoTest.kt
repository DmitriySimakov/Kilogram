package com.dmitrysimakov.kilogram.data.local.dao

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.util.DbTest
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
    
    @Test fun getParamList() {
        val params = getValue(dao.getParamList())
        assertThat(params.size, equalTo(testMuscles.size))
        
        // Ensure list is sorted by id
        for (i in 0..testMuscles.lastIndex) {
            assertThat(params[i], equalTo(FilterParam(testMuscles[i].name, false)))
        }
    }
    
    @Test fun getProgramDayParamList() {
        val programId = db.programDao().insert(Program(0, "PPL"))
        val programDayId = db.programDayDao().insert(ProgramDay(0, programId, 3, "Push"))
        db.programDayMuscleDao().insert(listOf(
                ProgramDayMuscle(programDayId, testMuscles[2].name),
                ProgramDayMuscle(programDayId, testMuscles[0].name)
        ))
        
        val params = getValue(dao.getProgramDayParamList(programDayId))
        assertThat(params.size, equalTo(testMuscles.size))
    
        // Ensure list is sorted by id
        assertThat(params[0], equalTo(FilterParam(testMuscles[0].name, true)))
        assertThat(params[1], equalTo(FilterParam(testMuscles[1].name, false)))
        assertThat(params[2], equalTo(FilterParam(testMuscles[2].name, true)))
    }
}
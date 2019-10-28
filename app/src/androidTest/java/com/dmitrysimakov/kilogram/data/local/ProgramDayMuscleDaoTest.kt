package com.dmitrysimakov.kilogram.data.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayMuscleDao
import com.dmitrysimakov.kilogram.data.relation.FilterParam
import com.dmitrysimakov.kilogram.util.getValue
import com.dmitrysimakov.kilogram.util.testMuscles
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test

class ProgramDayMuscleDaoTest : DbTest() {
    
    private lateinit var dao: ProgramDayMuscleDao
    
    @Before fun initDb() {
        dao = db.programDayMuscleDao()
    }
    
    @Test fun getParams() {
        val params = getValue(dao.getParams(6))
        assertThat(params.size, equalTo(testMuscles.size))
        
        // Ensure list is sorted by id
        assertThat(params[0], equalTo(FilterParam(testMuscles[0]._id, testMuscles[0].name, false)))
        assertThat(params[9], equalTo(FilterParam(testMuscles[9]._id, testMuscles[9].name, true)))
        assertThat(params[10], equalTo(FilterParam(testMuscles[10]._id, testMuscles[10].name, true)))
    }
}
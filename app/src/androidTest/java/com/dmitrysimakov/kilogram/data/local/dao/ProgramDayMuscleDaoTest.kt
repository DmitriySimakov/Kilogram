package com.dmitrysimakov.kilogram.data.local.dao

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.util.DbTest
import com.dmitrysimakov.kilogram.data.relation.FilterParam
import com.dmitrysimakov.kilogram.util.testMuscles
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test

class ProgramDayMuscleDaoTest : DbTest() {
    
    private lateinit var dao: ProgramDayMuscleDao
    
    @Before fun initDb() {
        dao = db.programDayMuscleDao()
    }
    
    @Test fun getParamList() = runBlocking {
        val params = dao.params(6)
        assertThat(params.size, equalTo(testMuscles.size))
        
        // Ensure list is sorted by id
        assertThat(params[0], equalTo(FilterParam(testMuscles[0].name, false)))
        assertThat(params[8], equalTo(FilterParam(testMuscles[8].name, true)))
        assertThat(params[9], equalTo(FilterParam(testMuscles[9].name, true)))
    }
}
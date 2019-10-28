package com.dmitrysimakov.kilogram.data.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.data.local.dao.TrainingMuscleDao
import com.dmitrysimakov.kilogram.data.relation.FilterParam
import com.dmitrysimakov.kilogram.util.getValue
import com.dmitrysimakov.kilogram.util.testMuscles
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test

class TrainingMuscleDaoTest : DbTest() {
    
    private lateinit var dao: TrainingMuscleDao
    
    @Before fun initDb() {
        dao = db.trainingMuscleDao()
    }
    
    @Test fun getParams() {
        val params = getValue(dao.getParams(2))
        assertThat(params.size, equalTo(testMuscles.size))
        
        // Ensure list is sorted by id
        assertThat(params[0], equalTo(FilterParam(testMuscles[0]._id, testMuscles[0].name, false)))
        assertThat(params[3], equalTo(FilterParam(testMuscles[3]._id, testMuscles[3].name, true)))
        assertThat(params[7], equalTo(FilterParam(testMuscles[7]._id, testMuscles[7].name, true)))
    }
}
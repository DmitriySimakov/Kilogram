package com.dmitrysimakov.kilogram.data.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.data.local.dao.ExerciseTypeDao
import com.dmitrysimakov.kilogram.data.relation.FilterParam
import com.dmitrysimakov.kilogram.util.getValue
import com.dmitrysimakov.kilogram.util.testExerciseTypes
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test

class ExerciseTypeDaoTest : DbTest() {
    
    private lateinit var dao: ExerciseTypeDao
    
    @Before fun initDb() {
        dao = db.exerciseTypeDao()
    }
    
    @Test fun getParams() {
        val params = getValue(dao.getParams())
        assertThat(params.size, equalTo(testExerciseTypes.size))
        
        // Ensure list is sorted by id
        for (i in 0..testExerciseTypes.lastIndex) {
            assertThat(params[i], equalTo(FilterParam(testExerciseTypes[i]._id, testExerciseTypes[i].name, false)))
        }
    }
}
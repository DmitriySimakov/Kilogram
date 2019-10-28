package com.dmitrysimakov.kilogram.data.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.data.local.dao.MechanicsTypeDao
import com.dmitrysimakov.kilogram.data.relation.FilterParam
import com.dmitrysimakov.kilogram.util.getValue
import com.dmitrysimakov.kilogram.util.testMechanicsTypes
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test

class MechanicsTypeDaoTest : DbTest() {
    
    private lateinit var dao: MechanicsTypeDao
    
    @Before fun initDb() {
        dao = db.mechanicsTypeDao()
    }
    
    @Test fun getParams() {
        val params = getValue(dao.getParams())
        assertThat(params.size, equalTo(testMechanicsTypes.size))
        
        // Ensure list is sorted by id
        for (i in 0..testMechanicsTypes.lastIndex) {
            assertThat(params[i], equalTo(FilterParam(testMechanicsTypes[i]._id, testMechanicsTypes[i].name, false)))
        }
    }
}
package com.dmitrysimakov.kilogram.data.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.data.local.dao.EquipmentDao
import com.dmitrysimakov.kilogram.data.relation.FilterParam
import com.dmitrysimakov.kilogram.util.getValue
import com.dmitrysimakov.kilogram.util.testEquipment
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test

class EquipmentDaoTest : DbTest() {
    
    private lateinit var dao: EquipmentDao
    
    @Before fun initDb() {
        dao = db.equipmentDao()
    }
    
    @Test fun getParams() {
        val params = getValue(dao.getParams())
        assertThat(params.size, equalTo(testEquipment.size))
        
        // Ensure list is sorted by id
        for (i in 0..testEquipment.lastIndex) {
            assertThat(params[i], equalTo(FilterParam(testEquipment[i]._id, testEquipment[i].name, false)))
        }
    }
}
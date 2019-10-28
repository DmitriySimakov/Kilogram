package com.dmitrysimakov.kilogram.data.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.relation.ProgramDayR
import com.dmitrysimakov.kilogram.util.getValue
import com.dmitrysimakov.kilogram.util.testProgramDays
import com.dmitrysimakov.kilogram.util.testPrograms
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test

class ProgramDayDaoTest : DbTest() {
    
    private lateinit var dao: ProgramDayDao
    
    @Before fun initDb() {
        dao = db.programDayDao()
    }
    
    @Test fun getNextProgramDayR() {
        val day = getValue(dao.getNextProgramDayR())
        val nextDay = testProgramDays[1]
        assertThat(day, equalTo((ProgramDayR(nextDay._id, nextDay.name, testPrograms[0].name))))
    }
    
    @Test fun getProgramDayR() {
        val day = getValue(dao.getProgramDayR(1))
        assertThat(day, equalTo(ProgramDayR(1, testProgramDays[0].name, testPrograms[0].name)))
    }
}
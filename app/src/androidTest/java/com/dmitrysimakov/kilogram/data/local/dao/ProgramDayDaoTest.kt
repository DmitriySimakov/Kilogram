package com.dmitrysimakov.kilogram.data.local.dao

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.util.DbTest
import com.dmitrysimakov.kilogram.data.relation.ProgramDayAndProgram
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
    
    @Test fun getNextProgramDay() {
        val day = getValue(dao.getNextProgramDay())
        val nextDay = testProgramDays[3]
        assertThat(day, equalTo((ProgramDayAndProgram(nextDay.name, testPrograms[0].name))))
    }
    
    @Test fun getProgramDayAndProgram() {
        val day = getValue(dao.getProgramDayAndProgram(1))
        assertThat(day, equalTo(ProgramDayAndProgram(testProgramDays[0].name, testPrograms[0].name)))
    }
}
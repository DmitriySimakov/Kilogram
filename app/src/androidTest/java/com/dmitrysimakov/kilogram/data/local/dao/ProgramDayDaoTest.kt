package com.dmitrysimakov.kilogram.data.local.dao

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dmitrysimakov.kilogram.data.relation.ProgramDayAndProgram
import com.dmitrysimakov.kilogram.util.DbTest
import com.dmitrysimakov.kilogram.util.testProgramDays
import com.dmitrysimakov.kilogram.util.testPrograms
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ProgramDayDaoTest : DbTest() {
    
    private lateinit var dao: ProgramDayDao
    
    @Before fun initDb() {
        dao = db.programDayDao()
    }
    
    @Test fun getNextProgramDay() = runBlockingTest {
        val day = dao.nextProgramDayAndProgram()
        val nextDay = testProgramDays[3]
        val sample = ProgramDayAndProgram(testProgramDays[3]._id, nextDay.name, testPrograms[0].name)
        assertThat(day, equalTo(sample))
    }
    
    @Test fun getProgramDayAndProgram() = runBlockingTest {
        val day = dao.programDayAndProgram(1)
        val sample = ProgramDayAndProgram(testProgramDays[0]._id, testProgramDays[0].name, testPrograms[0].name)
        assertThat(day, equalTo(sample))
    }
}
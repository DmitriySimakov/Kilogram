package com.dmitrysimakov.kilogram.data.local.dao

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.util.DbTest
import com.dmitrysimakov.kilogram.data.relation.DetailedTraining
import com.dmitrysimakov.kilogram.util.getValue
import com.dmitrysimakov.kilogram.util.testProgramDays
import com.dmitrysimakov.kilogram.util.testPrograms
import com.dmitrysimakov.kilogram.util.testTrainings
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test

class TrainingDaoTest : DbTest() {
    
    private lateinit var dao: TrainingDao
    
    @Before fun initDb() {
        dao = db.trainingDao()
    }
    
    @Test fun getDetailedTrainingList() {
        val list = getValue(dao.getDetailedTrainingList())
        assertThat(list.size, equalTo(testTrainings.size))
        
        val sampleList  = testTrainings.map { training ->
            val programDay = testProgramDays.find { it._id == training.program_day_id }
            val programDayName = programDay?.name
            val programName = testPrograms.find { it._id == programDay?.program_id }?.name
            DetailedTraining(
                    training._id,
                    training.start_time,
                    training.duration,
                    training.program_day_id,
                    programDayName,
                    programName
            )
        }.reversed()
        
        assertThat(list, equalTo(sampleList))
    }
}
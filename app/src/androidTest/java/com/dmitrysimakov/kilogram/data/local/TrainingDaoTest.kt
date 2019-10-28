package com.dmitrysimakov.kilogram.data.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.relation.TrainingR
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
    
    @Test fun getTrainingRList() {
        val list = getValue(dao.getTrainingRList())
        assertThat(list.size, equalTo(testTrainings.size))
        
        // Ensure list is sorted by id
        assertThat(list[0], equalTo(TrainingR(testTrainings[2]._id,
                testTrainings[2].program_day_id,
                testTrainings[2].start_time,
                testTrainings[2].duration)))
        assertThat(list[1], equalTo(TrainingR(testTrainings[2]._id,
                testTrainings[1].program_day_id,
                testTrainings[1].start_time,
                testTrainings[1].duration,
                testProgramDays[1].name,
                testPrograms[0].name)))
        assertThat(list[2], equalTo(TrainingR(testTrainings[2]._id,
                testTrainings[2].program_day_id,
                testTrainings[2].start_time,
                testTrainings[2].duration,
                testProgramDays[2].name,
                testPrograms[0].name)))
    }
}
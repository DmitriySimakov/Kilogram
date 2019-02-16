package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.entity.ProgramDay

@Dao
interface ProgramDayDao {
    
    @Query("SELECT * FROM program_day WHERE program_id = :programId ORDER BY number")
    fun getTrainingDays(programId: Long): LiveData<List<ProgramDay>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<ProgramDay>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(programDay: ProgramDay) : Long
    
    @Delete
    fun delete(day: ProgramDay)
}
package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.relation.ProgramDayR

@Dao
interface ProgramDayDao {
    
    @Query("SELECT * FROM program_day WHERE program_id = :programId ORDER BY num")
    fun getTrainingDays(programId: Long): LiveData<List<ProgramDay>>
    
    @Query("""
        SELECT next._id AS program_day_id, next.name AS program_day, p.name AS program
        FROM (
            SELECT pd.program_id, pd.num
            FROM program_day AS pd
            INNER JOIN training AS t ON pd._id = t.program_day_id
            ORDER BY t.date_time DESC
            LIMIT 1
        ) AS last
        INNER JOIN program_day AS next ON next.program_id = last.program_id
        AND next.num IN (last.num + 1, 1)
        INNER JOIN program AS p ON next.program_id = p._id
        ORDER BY next.num DESC
    """)
    fun getNextProgramDayR(): LiveData<ProgramDayR>
    
    @Query("""
        SELECT pd._id AS program_day_id, pd.name AS program_day, p.name AS program
        FROM program_day AS pd
        INNER JOIN program AS p
        WHERE pd._id = :id
    """)
    fun getProgramDayR(id: Long): LiveData<ProgramDayR>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<ProgramDay>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(programDay: ProgramDay): Long
    
    @Delete
    fun delete(day: ProgramDay)
}
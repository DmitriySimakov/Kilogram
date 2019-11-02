package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.relation.ProgramDayAndProgram

@Dao
interface ProgramDayDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<ProgramDay>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programDay: ProgramDay): Long
    
    @Query("SELECT * FROM program_day WHERE program_id = :programId ORDER BY indexNumber")
    fun getProgramDayList(programId: Long): LiveData<List<ProgramDay>>
    
    @Query("""
        SELECT pd._id AS program_day_id, pd.name AS program_day, p.name AS program
        FROM program_day AS pd
        INNER JOIN program AS p
        WHERE pd._id = :id
    """)
    fun getProgramDayAndProgram(id: Long): LiveData<ProgramDayAndProgram?>
    
    @Query("""
        SELECT next._id AS program_day_id, next.name AS program_day, p.name AS program
        FROM (
            SELECT pd.program_id, pd.indexNumber
            FROM program_day AS pd
            INNER JOIN training AS t ON pd._id = t.program_day_id
            ORDER BY t.start_time DESC
            LIMIT 1
        ) AS last
        INNER JOIN program_day AS next ON next.program_id = last.program_id
        AND next.indexNumber IN (last.indexNumber + 1, 1)
        INNER JOIN program AS p ON next.program_id = p._id
        ORDER BY next.indexNumber DESC
    """)
    fun getNextProgramDay(): LiveData<ProgramDayAndProgram?>
    
    @Update
    suspend fun update(programDayList: List<ProgramDay>)
    
    @Query("DELETE FROM program_day WHERE _id = :id")
    suspend fun delete(id: Long)
}
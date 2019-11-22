package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.local.relation.ProgramDayAndProgram
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgramDayDao {
    
    @Query("SELECT * FROM program_day WHERE program_id = :programId ORDER BY indexNumber")
    fun programDaysFlow(programId: Long): Flow<List<ProgramDay>>
    
    @Query("""
        SELECT pd._id AS program_day_id, pd.name AS program_day, p.name AS program
        FROM program_day AS pd
        INNER JOIN program AS p
        WHERE pd._id = :id
    """)
    suspend fun programDayAndProgram(id: Long): ProgramDayAndProgram?
    
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
    suspend fun nextProgramDayAndProgram(): ProgramDayAndProgram?
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programDays: List<ProgramDay>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programDay: ProgramDay): Long
    
    
    @Update
    suspend fun update(programDayList: List<ProgramDay>)
    
    
    @Query("DELETE FROM program_day WHERE _id = :id")
    suspend fun delete(id: Long)
}
package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.local.relation.ProgramDayAndProgram
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgramDayDao {
    
    @Query("SELECT * FROM ProgramDay WHERE programId = :programId ORDER BY indexNumber")
    fun programDaysFlow(programId: Long): Flow<List<ProgramDay>>
    
    @Query("""
        SELECT PD.id AS programDayId, PD.name AS programDay, P.name AS program
        FROM ProgramDay AS PD
        INNER JOIN program AS P
        WHERE PD.id = :id
    """)
    suspend fun programDayAndProgram(id: Long): ProgramDayAndProgram?
    
    @Query("""
        SELECT Next.id AS programDayId, Next.name AS programDay, P.name AS program
        FROM (
            SELECT PD.programId, PD.indexNumber
            FROM ProgramDay AS PD
            INNER JOIN training AS T ON PD.id = T.programDayId
            ORDER BY T.startDateTime DESC
            LIMIT 1
        ) AS Last
        INNER JOIN ProgramDay AS Next ON Next.programId = Last.programId
        AND Next.indexNumber IN (Last.indexNumber + 1, 1)
        INNER JOIN program AS P ON Next.programId = P.id
        ORDER BY Next.indexNumber DESC
    """)
    suspend fun nextProgramDayAndProgram(): ProgramDayAndProgram?
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programDays: List<ProgramDay>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programDay: ProgramDay): Long
    
    
    @Update
    suspend fun update(programDayList: List<ProgramDay>)
    
    
    @Query("DELETE FROM ProgramDay WHERE id = :id")
    suspend fun delete(id: Long)
}
package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.*
import com.dmitrysimakov.kilogram.data.model.ProgramDay
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgramDayDao {
    
    @Query("SELECT * FROM ProgramDay WHERE programId = :programId ORDER BY indexNumber")
    fun programDaysFlow(programId: String): Flow<List<ProgramDay>>
    
    @Query("SELECT * FROM ProgramDay WHERE programId = :programId ORDER BY indexNumber")
    suspend fun programDays(programId: String): List<ProgramDay>
    
    @Query("SELECT * FROM ProgramDay WHERE id = :id")
    suspend fun programDay(id: String): ProgramDay
    
    @Query("""
        SELECT Next.programId, Next.indexNumber, Next.name, Next.description, Next.lastUpdate, Next.deleted, Next.id
        FROM (
            SELECT PD.programId, PD.indexNumber
            FROM ProgramDay AS PD
            INNER JOIN training AS T ON PD.id = T.programDayId
            ORDER BY T.startDateTime DESC
            LIMIT 1
        ) AS Last
        INNER JOIN ProgramDay AS Next ON Next.programId = Last.programId
        AND Next.indexNumber IN (Last.indexNumber + 1, 1)
        ORDER BY Next.indexNumber DESC
    """)
    suspend fun nextProgramDay(): ProgramDay?
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programDays: List<ProgramDay>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programDay: ProgramDay)
    
    
    @Update
    suspend fun update(programDayList: List<ProgramDay>)
    
    
    @Query("DELETE FROM ProgramDay WHERE id = :id")
    suspend fun delete(id: String)
}
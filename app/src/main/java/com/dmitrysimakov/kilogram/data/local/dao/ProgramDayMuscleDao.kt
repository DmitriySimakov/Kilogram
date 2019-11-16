package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayMuscle
import com.dmitrysimakov.kilogram.data.relation.FilterParam

@Dao
interface ProgramDayMuscleDao {
    
    @Query("""SELECT m.name, CASE WHEN pdm.program_day_id IS NULL THEN 0 ELSE 1 END AS is_active
        FROM muscle AS m
        LEFT JOIN program_day_muscle AS pdm
        ON pdm.program_day_id = :programId AND pdm.muscle = m.name
    """)
    suspend fun params(programId: Long): List<FilterParam>
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programDayMuscles: List<ProgramDayMuscle>)
}
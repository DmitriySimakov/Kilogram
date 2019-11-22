package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayTarget
import com.dmitrysimakov.kilogram.data.local.relation.FilterParam

@Dao
interface ProgramDayTargetDao {
    
    @Query("""SELECT et.name, CASE WHEN pdt.program_day_id IS NULL THEN 0 ELSE 1 END AS is_active
        FROM exercise_target AS et
        LEFT JOIN program_day_targets AS pdt
        ON pdt.program_day_id = :programId AND pdt.exercise_target = et.name
    """)
    suspend fun params(programId: Long): List<FilterParam>
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programDayTargets: List<ProgramDayTarget>)
}
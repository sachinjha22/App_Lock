package com.karmakarin.safebox.db.pattern

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PatternDao {

    @Transaction
    open fun createPattern(patternEntity: PatternEntity) {
        deletePattern()
        insertPattern(patternEntity)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPattern(patternEntity: PatternEntity)

    @Query("SELECT * FROM pattern LIMIT 1")
    abstract fun getPattern(): Flow<PatternEntity>

    @Query("SELECT count(*) FROM pattern")
    abstract fun isPatternCreated(): Flow<Int>

    @Query("DELETE FROM pattern")
    abstract fun deletePattern()
}
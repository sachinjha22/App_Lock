package com.karmakarin.safebox.db.pattern

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "pattern")
@TypeConverters(PatternTypeConverter::class)
data class PatternEntity(
    @PrimaryKey
    @ColumnInfo(name = "pattern_metadata")
    val patternMetadata: PatternDotMetadata
) : Serializable
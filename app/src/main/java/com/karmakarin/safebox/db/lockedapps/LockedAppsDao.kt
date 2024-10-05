package com.karmakarin.safebox.db.lockedapps

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LockedAppsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun lockApp(lockedAppEntity: LockedAppEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun lockApps(lockedAppEntityList: List<LockedAppEntity>)

    @Query("SELECT * FROM locked_app")
    abstract fun getLockedApps(): Flow<List<LockedAppEntity>>

    @Query("SELECT * FROM locked_app")
    abstract fun getLockedAppsSync(): List<LockedAppEntity>

    @Query("DELETE FROM locked_app WHERE packageName = :packageName")
    abstract fun unlockApp(packageName: String)

    @Query("DELETE FROM locked_app")
    abstract fun unlockAll()
}
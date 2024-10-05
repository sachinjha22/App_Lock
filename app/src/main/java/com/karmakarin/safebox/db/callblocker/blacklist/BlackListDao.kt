package com.karmakarin.safebox.db.callblocker.blacklist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BlackListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addToBlacklist(blackListItemEntity: BlackListItemEntity)

    @Query("SELECT * FROM blacklist")
    abstract fun getBlackList(): Flow<List<BlackListItemEntity>>

    @Query("DELETE FROM blacklist WHERE blacklist_id = :blackListId")
    abstract fun delete(blackListId: Int)

}
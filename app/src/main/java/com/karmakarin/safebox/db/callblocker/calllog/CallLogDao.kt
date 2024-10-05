package com.momentolabs.app.security.applocker.data.database.callblocker.calllog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karmakarin.safebox.db.callblocker.calllog.CallLogItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CallLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addToLog(callLogItemEntity: CallLogItemEntity)

    @Query("SELECT * FROM call_log")
    abstract fun getCallLogs(): Flow<List<CallLogItemEntity>>

    @Query("DELETE FROM call_log WHERE log_id = :logId")
    abstract fun delete(logId: Int)
}
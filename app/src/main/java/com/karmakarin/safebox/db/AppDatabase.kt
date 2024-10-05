package com.karmakarin.safebox.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.karmakarin.safebox.db.bookmark.BookmarkDao
import com.karmakarin.safebox.db.bookmark.BookmarkEntity
import com.karmakarin.safebox.db.callblocker.blacklist.BlackListDao
import com.karmakarin.safebox.db.callblocker.blacklist.BlackListItemEntity
import com.karmakarin.safebox.db.callblocker.calllog.CallLogItemEntity
import com.karmakarin.safebox.db.callblocker.calllog.DateTypeConverters
import com.karmakarin.safebox.db.lockedapps.LockedAppEntity
import com.karmakarin.safebox.db.lockedapps.LockedAppsDao
import com.karmakarin.safebox.db.pattern.PatternDao
import com.karmakarin.safebox.db.pattern.PatternEntity
import com.karmakarin.safebox.db.vault.VaultMediaDao
import com.karmakarin.safebox.db.vault.VaultMediaEntity
import com.momentolabs.app.security.applocker.data.database.callblocker.calllog.CallLogDao

@Database(
    entities = [LockedAppEntity::class, PatternEntity::class, BookmarkEntity::class, VaultMediaEntity::class, BlackListItemEntity::class, CallLogItemEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getLockedAppsDao(): LockedAppsDao

    abstract fun getPatternDao(): PatternDao

    abstract fun getBookmarkDao(): BookmarkDao

    abstract fun getVaultMediaDao(): VaultMediaDao

    abstract fun getBlackListDao(): BlackListDao

    abstract fun getCallLogDao(): CallLogDao
}
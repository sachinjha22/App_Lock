package com.karmakarin.safebox.di.db

import android.content.Context
import androidx.room.Room
import com.karmakarin.safebox.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, AppDatabase::class.java, "applocker.db")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideLockedAppsDao(db: AppDatabase) = db.getLockedAppsDao()

    @Provides
    @Singleton
    fun providePatternDao(db: AppDatabase) = db.getPatternDao()

    @Provides
    @Singleton
    fun provideBookmarkDao(db: AppDatabase) = db.getBookmarkDao()

    @Provides
    @Singleton
    fun provideVaultMediaDao(db: AppDatabase) = db.getVaultMediaDao()

    @Provides
    @Singleton
    fun provideBlackListDao(db: AppDatabase) = db.getBlackListDao()

    @Provides
    @Singleton
    fun provideCallLogDao(db: AppDatabase) = db.getCallLogDao()
}
package com.karmakarin.safebox.db.vault

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class VaultMediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addToVault(vaultMediaEntity: VaultMediaEntity)

    @Query("DELETE FROM vault_media WHERE original_path = :originalPath")
    abstract fun removeFromVault(originalPath: String)

    @Query("SELECT * FROM vault_media WHERE media_type = 'type_image'")
    abstract fun getVaultImages(): Flow<List<VaultMediaEntity>>

    @Query("SELECT * FROM vault_media WHERE media_type = 'type_video'")
    abstract fun getVaultVideos(): Flow<List<VaultMediaEntity>>
}
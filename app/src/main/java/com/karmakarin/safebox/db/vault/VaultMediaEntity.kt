package com.karmakarin.safebox.db.vault

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "vault_media")
data class VaultMediaEntity(
    @PrimaryKey
    @ColumnInfo(name = "original_path")
    val originalPath: String,
    @ColumnInfo(name = "original_file_name")
    val originalFileName: String?,
    @ColumnInfo(name = "encrypted_path")
    val encryptedPath: String?,
    @ColumnInfo(name = "encrypted_preview_path")
    val encryptedPreviewPath: String?,
    @ColumnInfo(name = "media_type")
    val mediaType: String?
) : Parcelable {

    @IgnoredOnParcel
    @Ignore
    var decryptedPreviewCachePath: String = ""

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
        decryptedPreviewCachePath = parcel.readString()!!
    }

    annotation class IgnoredOnParcel

    fun getEncryptedPreviewFileName(): String {
        val slashIndex = encryptedPreviewPath?.lastIndexOf('/')
        return encryptedPreviewPath?.substring((slashIndex ?: 0) + 1) ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(originalPath)
        parcel.writeString(originalFileName)
        parcel.writeString(encryptedPath)
        parcel.writeString(encryptedPreviewPath)
        parcel.writeString(mediaType)
        parcel.writeString(decryptedPreviewCachePath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VaultMediaEntity> {
        override fun createFromParcel(parcel: Parcel): VaultMediaEntity {
            return VaultMediaEntity(parcel)
        }

        override fun newArray(size: Int): Array<VaultMediaEntity?> {
            return arrayOfNulls(size)
        }
    }
}
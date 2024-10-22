package com.karmakarin.safebox.support.filemanager

data class FileOperationRequest(
    val fileName: String,
    val fileExtension: FileExtension,
    val directoryType: DirectoryType
)
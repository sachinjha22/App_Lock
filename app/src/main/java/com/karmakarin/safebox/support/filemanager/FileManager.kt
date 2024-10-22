package com.karmakarin.safebox.support.filemanager

import android.content.Context
import android.os.Environment
import com.karmakarin.safebox.util.Constants.EXTERNAL_FOLDER_INTRUDERS
import com.karmakarin.safebox.util.Constants.EXTERNAL_FOLDER_VAULT
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Locale
import javax.inject.Inject

class FileManager @Inject constructor(@ApplicationContext context: Context) {

    private lateinit var context: Context

    init {
        this.context = context
    }

    enum class SubFolder(val subFolderPath: String) {
        VAULT(EXTERNAL_FOLDER_VAULT),
        INTRUDERS(EXTERNAL_FOLDER_INTRUDERS)

    }

    fun createFile(fileOperationRequest: FileOperationRequest, subFolder: SubFolder): File {
        val folder = when (fileOperationRequest.directoryType) {
            DirectoryType.CACHE -> getCacheDir()
            DirectoryType.EXTERNAL -> getExternalDirectory(subFolder)
        }

        return File(
            folder,
            fileOperationRequest.fileName + fileOperationRequest.fileExtension.extension
        )
    }

    suspend fun deleteFile(filePath: String): Boolean {
        return withContext(Dispatchers.IO) {
            val file = File(filePath)
            file.delete()
        }
    }

    fun getFile(fileOperationRequest: FileOperationRequest, subFolder: SubFolder): File? {
        val folder = when (fileOperationRequest.directoryType) {
            DirectoryType.CACHE -> getCacheDir()
            DirectoryType.EXTERNAL -> getExternalDirectory(subFolder)
        }

        return File(folder.absolutePath + "/" + fileOperationRequest.fileName + fileOperationRequest.fileExtension.extension)
    }

    fun getSubFiles(folder: File, extension: FileExtension): List<File> {
        if (folder.isFile || folder.exists().not()) {
            return arrayListOf()
        }

        val files = folder.listFiles() ?: arrayOf()
        return files.filter {
            it.name.lowercase(Locale.getDefault())
                .endsWith(extension.extension.lowercase(Locale.getDefault()))
        }
    }

    fun createFileInCache(fileName: String): File {
        return File(context.cacheDir, "$fileName.jpg")
    }

    fun isFileExist(filePath: String): Boolean {
        return File(filePath).exists()
    }

    fun isFileInCache(fileName: String): Boolean {
        val cacheFile = File(context.cacheDir, fileName)
        return cacheFile.exists()
    }

    fun getExternalDirectory(subFolder: SubFolder): File {
        val appPath = Environment.getExternalStorageDirectory().toString() + subFolder.subFolderPath
        val folder = File(appPath)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        return folder
    }

    private fun getCacheDir(): File = context.cacheDir

}
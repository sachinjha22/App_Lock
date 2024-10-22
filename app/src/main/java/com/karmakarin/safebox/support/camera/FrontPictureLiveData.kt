package com.karmakarin.safebox.support.camera

import android.app.Application
import android.graphics.SurfaceTexture
import android.hardware.Camera
import androidx.lifecycle.MutableLiveData
import com.karmakarin.safebox.util.Constants.NO_CAMERA_ID
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class FrontPictureLiveData(val app: Application, private val destinationImageFile: File) :
    MutableLiveData<FrontPictureState>() {
    private enum class State {
        IDLE, TAKING, TAKEN, ERROR
    }

    private var camera: Camera? = null

    private var state: State = State.IDLE

    override fun onInactive() {
        super.onInactive()
        stopCamera()
    }

    fun takePicture() {
        if (state == State.TAKEN || state == State.TAKING) {
            return
        }

        state = State.TAKING
        startCamera()
        camera?.takePicture(
            null,
            null
        ) { data, _ -> savePicture(data) }
    }

    private fun startCamera() {
        val dummy = SurfaceTexture(0)

        try {
            val cameraId = getFrontCameraId()
            if (cameraId == NO_CAMERA_ID) {
                value = FrontPictureState.Error(IllegalStateException("No front camera found"))
                state = State.ERROR
                return
            }
            camera = Camera.open(cameraId).also {
                it.setPreviewTexture(dummy)
                it.startPreview()
            }
            value = FrontPictureState.Started
        } catch (e: RuntimeException) {
            value = FrontPictureState.Error(e)
            state = State.ERROR
        }
    }

    private fun stopCamera() {
        camera?.stopPreview()
        camera?.release()
        camera = null
        value = FrontPictureState.Destroyed
    }

    private fun getFrontCameraId(): Int {
        var camId =
            NO_CAMERA_ID
        val numberOfCameras = Camera.getNumberOfCameras()
        val ci = Camera.CameraInfo()

        for (i in 0 until numberOfCameras) {
            Camera.getCameraInfo(i, ci)
            if (ci.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                camId = i
            }
        }

        return camId
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun savePicture(data: ByteArray) {
        GlobalScope.launch {
            try {
                val filePath = saveImage(data)
                state = State.TAKEN
                value = FrontPictureState.Taken(filePath)
                refreshFileSystem(filePath)
            } catch (e: Exception) {
                state = State.ERROR
                value = FrontPictureState.Error(e)
            }
        }
    }

    private suspend fun saveImage(data: ByteArray): String {
        return withContext(Dispatchers.IO) {
            try {
                destinationImageFile.let { pictureFile ->
                    FileOutputStream(pictureFile).use { fos ->
                        fos.write(data)
                    }
                    pictureFile.absolutePath
                } ?: throw IllegalArgumentException("Destination file cannot be null")
            } catch (e: Exception) {
                throw e // rethrow the exception to be handled by the caller
            }
        }
    }

    private fun refreshFileSystem(originalPath: String) {
//        MediaScannerConnector.scan(app, originalPath)
    }
}
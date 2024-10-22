package com.karmakarin.safebox.support.camera

sealed class FrontPictureState {
    class Taken(val filePath: String) : FrontPictureState()
    class Error(val error: Throwable) : FrontPictureState()
    data object Started : FrontPictureState()
    data object Destroyed : FrontPictureState()
}
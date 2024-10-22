package com.karmakarin.safebox.ui.view.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karmakarin.safebox.db.pattern.PatternDao
import com.karmakarin.safebox.db.pattern.PatternDot
import com.karmakarin.safebox.services.PatternValidatorFunction
import com.karmakarin.safebox.support.OCDataSource
import com.karmakarin.safebox.support.filemanager.DirectoryType
import com.karmakarin.safebox.support.filemanager.FileExtension
import com.karmakarin.safebox.support.filemanager.FileManager
import com.karmakarin.safebox.support.filemanager.FileOperationRequest
import com.karmakarin.safebox.support.finger.FingerPrintLiveData
import com.karmakarin.safebox.support.overlay.OverlayValidateType
import com.karmakarin.safebox.ui.view.state.GradientItemViewState
import com.karmakarin.safebox.ui.view.state.OverlayViewState
import com.karmakarin.safebox.util.global.GradientBackgroundDataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class OverlayViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val patternDao: PatternDao,
    private val appLockerPref: OCDataSource,
    private val fileManager: FileManager
) : ViewModel() {

    private val _patternValidationViewStateLiveData = MediatorLiveData<OverlayViewState>().apply {
        value = OverlayViewState(
            isHiddenDrawingMode = appLockerPref.getHiddenDrawingMode(),
            isFingerPrintMode = appLockerPref.getFingerPrintEnabled()
        )
    }
    val patternValidationViewStateLiveData: LiveData<OverlayViewState> =
        _patternValidationViewStateLiveData

    private val _selectedBackgroundDrawableLiveData = MutableLiveData<GradientItemViewState>()
    val selectedBackgroundDrawableLiveData: LiveData<GradientItemViewState> =
        _selectedBackgroundDrawableLiveData

    private val fingerPrintLiveData = FingerPrintLiveData(context)

    private val _patternDrawnFlow =
        MutableSharedFlow<List<PatternDot>>() // Use Flow for drawn patterns

    init {
        viewModelScope.launch {
            observePatternAndValidate()
            observeFingerPrintLiveData()
            loadSelectedBackgroundDrawable()
        }
    }

    private suspend fun observePatternAndValidate() {
        patternDao.getPattern().map { patternEntity ->
            patternEntity.patternMetadata.pattern
        }.collect { existingPattern ->
            existingPattern.let { patternList ->
                _patternDrawnFlow.collect { drawnPattern ->
                    val isValidated = PatternValidatorFunction().apply(patternList, drawnPattern)

                    withContext(Dispatchers.Main) {
                        _patternValidationViewStateLiveData.value = OverlayViewState(
                            overlayValidateType = OverlayValidateType.TYPE_PATTERN,
                            isDrawnCorrect = isValidated,
                            isHiddenDrawingMode = appLockerPref.getHiddenDrawingMode(),
                            isIntrudersCatcherMode = appLockerPref.getIntrudersCatcherEnabled(),
                            isFingerPrintMode = appLockerPref.getFingerPrintEnabled()
                        )
                    }
                }
            }
        }
    }

    private fun observeFingerPrintLiveData() {
        _patternValidationViewStateLiveData.addSource(fingerPrintLiveData) { fingerPrintResult ->
            _patternValidationViewStateLiveData.value = OverlayViewState(
                overlayValidateType = OverlayValidateType.TYPE_FINGERPRINT,
                fingerPrintResultData = fingerPrintResult,
                isHiddenDrawingMode = appLockerPref.getHiddenDrawingMode(),
                isIntrudersCatcherMode = appLockerPref.getIntrudersCatcherEnabled(),
                isFingerPrintMode = appLockerPref.getFingerPrintEnabled()
            )
        }
    }

    private fun loadSelectedBackgroundDrawable() {
        val selectedBackgroundId = appLockerPref.getSelectedBackgroundId()
        GradientBackgroundDataProvider.gradientViewStateList.forEach {
            if (it.id == selectedBackgroundId) {
                _selectedBackgroundDrawableLiveData.value = it
            }
        }
    }

    // Function to handle user drawing a pattern
    fun onPatternDrawn(pattern: List<PatternDot>) {
        viewModelScope.launch {
            _patternDrawnFlow.emit(pattern)
        }
    }

    // Function to create intruder picture image file
    fun getIntruderPictureImageFile(): File {
        val fileSuffix = System.currentTimeMillis()
        val fileOperationRequest =
            FileOperationRequest("IMG_$fileSuffix", FileExtension.JPEG, DirectoryType.EXTERNAL)
        return fileManager.createFile(fileOperationRequest, FileManager.SubFolder.INTRUDERS)
    }
}


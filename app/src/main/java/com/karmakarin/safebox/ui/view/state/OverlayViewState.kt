package com.karmakarin.safebox.ui.view.state

import android.content.Context
import android.view.View
import com.karmakarin.safebox.R
import com.karmakarin.safebox.support.finger.FingerPrintResult
import com.karmakarin.safebox.support.finger.FingerPrintResultData
import com.karmakarin.safebox.support.overlay.OverlayValidateType


data class OverlayViewState(
    val overlayValidateType: OverlayValidateType? = null,
    val isDrawnCorrect: Boolean? = null,
    val fingerPrintResultData: FingerPrintResultData? = null,
    val isHiddenDrawingMode: Boolean = false,
    val isFingerPrintMode: Boolean = false,
    val isIntrudersCatcherMode: Boolean = false
) {

    fun getPromptMessage(context: Context): String {
        return when (overlayValidateType) {
            OverlayValidateType.TYPE_PATTERN -> {
                when (isDrawnCorrect) {
                    true -> context.getString(R.string.overlay_prompt_pattern_title_correct)
                    false -> context.getString(R.string.overlay_prompt_pattern_title_wrong)
                    null -> context.getString(R.string.overlay_prompt_pattern_title)
                }
            }

            OverlayValidateType.TYPE_FINGERPRINT -> {
                when (fingerPrintResultData?.fingerPrintResult) {
                    FingerPrintResult.SUCCESS -> context.getString(R.string.overlay_prompt_fingerprint_title_correct)
                    FingerPrintResult.NOT_MATCHED -> context.getString(
                        R.string.overlay_prompt_fingerprint_title_wrong,
                        fingerPrintResultData.availableTimes.toString()
                    )

                    FingerPrintResult.ERROR -> context.getString(R.string.overlay_prompt_fingerprint_title_error)
                    else -> context.getString(R.string.overlay_prompt_fingerprint_title)
                }
            }

            else -> context.getString(R.string.overlay_prompt_pattern_title)
        }
    }

    fun getFingerPrintIconVisibility(): Int =
        if (isFingerPrintMode) View.VISIBLE else View.INVISIBLE

}
package com.karmakarin.safebox.support.finger

import android.content.Context
import androidx.lifecycle.MutableLiveData

class FingerPrintLiveData(context: Context) : MutableLiveData<FingerPrintResultData>(),
    BaseFingerprint.ExceptionListener {

    private val fingerprintIdentify: FingerprintIdentify = FingerprintIdentify(context)

    init {
        try {
            fingerprintIdentify.setSupportAndroidL(true)
            fingerprintIdentify.init()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    override fun onActive() {
        super.onActive()
        fingerprintIdentify.startIdentify(3, object : BaseFingerprint.IdentifyListener {
            override fun onSucceed() {
                value = FingerPrintResultData.matched()
            }

            override fun onNotMatch(availableTimes: Int) {
                value = FingerPrintResultData.notMatched(availableTimes)
            }

            override fun onFailed(isDeviceLocked: Boolean) {
                value = FingerPrintResultData.error("Fingerprint error")
            }

            override fun onStartFailedByDeviceLocked() {
                value = FingerPrintResultData.error("Fingerprint error")
            }
        })
    }

    override fun onInactive() {
        super.onInactive()
        fingerprintIdentify.cancelIdentify()
    }

    override fun onCatchException(exception: Throwable?) {
        value = FingerPrintResultData.error(exception?.message ?: "")
    }

}
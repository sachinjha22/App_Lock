package com.karmakarin.safebox.repo

import com.karmakarin.safebox.support.OCDataSource
import javax.inject.Inject

class UserPrefRepository @Inject constructor(private val ocDataSource: OCDataSource) {

    private var isRateUsAskedInThisSession = false

    fun setRateUsAsked() {
        isRateUsAskedInThisSession = true
    }

    fun setUserRateUs() {
        ocDataSource.setUserRateUs()
    }

    fun isUserRateUs(): Boolean {
        return isRateUsAskedInThisSession || ocDataSource.isUserRateUs()
    }

    fun isPrivacyPolicyAccepted(): Boolean {
        return ocDataSource.isPrivacyPolicyAccepted()
    }

    fun endSession() {
        isRateUsAskedInThisSession = false
    }
}
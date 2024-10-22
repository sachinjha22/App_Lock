package com.karmakarin.safebox.ui.view.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.karmakarin.safebox.db.pattern.PatternDao
import com.karmakarin.safebox.support.OCDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class PrivacyPolicyViewModel @Inject constructor(
    private val appLockerPref: OCDataSource
) : ViewModel() {

    fun acceptPrivacyPolicy() {
        appLockerPref.acceptPrivacyPolicy()
    }
}
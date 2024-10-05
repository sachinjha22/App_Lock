package com.karmakarin.safebox.ui.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karmakarin.safebox.db.pattern.PatternDao
import com.karmakarin.safebox.repo.UserPrefRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val patternDao: PatternDao,
    private val userPreferencesRepository: UserPrefRepository
) : ViewModel() {

    private val _patternCreationNeedLiveData = MutableLiveData<Boolean>()

    val getPatternCreationNeedLiveData: LiveData<Boolean> = _patternCreationNeedLiveData

    private var appLaunchValidated = false

    init {
        checkPatternCreationStatus()
    }

    private fun checkPatternCreationStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val pattern = patternDao.isPatternCreated()
            withContext(Dispatchers.Main) {
                pattern.collect { _patternCreationNeedLiveData.value = it == 0 }
            }
        }
    }

    fun onAppLaunchValidated() {
        appLaunchValidated = true
    }

    fun isPrivacyPolicyAccepted() = userPreferencesRepository.isPrivacyPolicyAccepted()

    fun isAppLaunchValidated(): Boolean = appLaunchValidated

    override fun onCleared() {
        super.onCleared()
        appLaunchValidated = false
        userPreferencesRepository.endSession()
    }
}
package com.karmakarin.safebox.ui.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karmakarin.safebox.db.lockedapps.LockedAppEntity
import com.karmakarin.safebox.db.lockedapps.LockedAppsDao
import com.karmakarin.safebox.ui.view.state.AddSectionHeaderViewStateFunction
import com.karmakarin.safebox.ui.view.state.AppLockItemBaseViewState
import com.karmakarin.safebox.ui.view.state.AppLockItemItemViewState
import com.karmakarin.safebox.ui.view.state.LockedAppListViewStateCreator
import com.karmakarin.safebox.util.AppDataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecurityViewModel @Inject constructor(
    private val appDataProvider: AppDataProvider,
    private val lockedAppsDao: LockedAppsDao
) : ViewModel() {

    private val appDataViewStateListLiveData = MutableLiveData<List<AppLockItemBaseViewState>>()

    init {
        fetchAppData()
    }

    private fun fetchAppData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val installedApps = appDataProvider.fetchInstalledAppList()
                val lockedApps = lockedAppsDao.getLockedApps()

//                val appListViewState = LockedAppListViewStateCreator.create(installedApps, lockedApps)
//                val appDataViewStateList = AddSectionHeaderViewStateFunction().apply(appListViewState)

                viewModelScope.launch {
                    LockedAppListViewStateCreator.create(installedApps, lockedApps)
                        .collect { appListViewState ->
                            val appDataViewStateList =
                                AddSectionHeaderViewStateFunction().apply(appListViewState)
                            appDataViewStateListLiveData.postValue(appDataViewStateList)
                        }
                }
//                withContext(Dispatchers.Main) {
//                    appDataViewStateListLiveData.value = appDataViewStateList
//                }
            } catch (e: Exception) {
                e.printStackTrace()// Handle any errors here, possibly logging or showing a message to the user
            }
        }
    }

    fun getAppDataListLiveData(): LiveData<List<AppLockItemBaseViewState>> =
        appDataViewStateListLiveData

    fun lockApp(appLockItemViewState: AppLockItemItemViewState) {
        viewModelScope.launch(Dispatchers.IO) {
            lockedAppsDao.lockApp(LockedAppEntity(appLockItemViewState.appData.packageName))
        }
    }

    fun unlockApp(appLockItemViewState: AppLockItemItemViewState) {
        viewModelScope.launch(Dispatchers.IO) {
            lockedAppsDao.unlockApp(appLockItemViewState.appData.packageName)
        }
    }
}

package com.karmakarin.safebox.ui.view.state

import com.karmakarin.safebox.db.lockedapps.AppData
import com.karmakarin.safebox.db.lockedapps.LockedAppEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class LockedAppListViewStateCreator() {
    fun create(
        installedApps: List<AppData>,
        lockedApps: List<LockedAppEntity>
    ): List<AppLockItemItemViewState> {
        val itemViewStateList: ArrayList<AppLockItemItemViewState> = arrayListOf()

        installedApps.forEach { installedApp ->
            val itemViewState = AppLockItemItemViewState(installedApp)

            // Check if the installed app is locked and set the isLocked property
            lockedApps.forEach { lockedApp ->
                if (installedApp.packageName == lockedApp.packageName) {
                    itemViewState.isLocked = true
                }
            }

            itemViewStateList.add(itemViewState)
        }

        return itemViewStateList
    }

    companion object {

        // Replace Observable.combineLatest with Flow.combine
        fun create(
            appDataFlow: Flow<List<AppData>>,
            lockedAppsFlow: Flow<List<LockedAppEntity>>
        ): Flow<List<AppLockItemItemViewState>> {
            return combine(appDataFlow, lockedAppsFlow) { installedApps, lockedApps ->
                LockedAppListViewStateCreator().create(installedApps, lockedApps)
            }
        }
    }
}
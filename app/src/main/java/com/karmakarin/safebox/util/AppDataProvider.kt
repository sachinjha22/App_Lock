package com.karmakarin.safebox.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import com.karmakarin.safebox.db.lockedapps.AppData
import com.karmakarin.safebox.db.lockedapps.LockedAppEntity
import com.karmakarin.safebox.db.lockedapps.LockedAppsDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppDataProvider @Inject constructor(
    @ApplicationContext val context: Context,
    private val appsDao: LockedAppsDao
) {

    // Replacing RxJava Single with a suspend function
    @SuppressLint("QueryPermissionsNeeded")
    fun fetchInstalledAppList(): Flow<List<AppData>> = flow {
        // Perform the I/O operation on a background thread (Dispatchers.IO)
        val appDataList = withContext(Dispatchers.IO) {
            val mainIntent = Intent(Intent.ACTION_MAIN, null)
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            val resolveInfoList: List<ResolveInfo> =
                context.packageManager.queryIntentActivities(mainIntent, 0)

            val appDataList: ArrayList<AppData> = arrayListOf()
            resolveInfoList.forEach { resolveInfo ->
                with(resolveInfo) {
                    if (activityInfo.packageName != context.packageName) {
                        val mainActivityName =
                            activityInfo.name.substring(activityInfo.name.lastIndexOf(".") + 1)
                        val appData = AppData(
                            appName = loadLabel(context.packageManager) as String,
                            packageName = "${activityInfo.packageName}/$mainActivityName",
                            appIconDrawable = loadIcon(context.packageManager)
                        )
                        appDataList.add(appData)
                    }
                }
            }

            // Fetch locked apps synchronously
            val lockedAppList = appsDao.getLockedAppsSync()

            // Return the ordered list of apps
            orderAppsByLockStatus(appDataList, lockedAppList)
        }
        // Emit the result
        emit(appDataList)
    }

    // Helper function to order apps by lock status
    private fun orderAppsByLockStatus(
        allApps: List<AppData>,
        lockedApps: List<LockedAppEntity>
    ): List<AppData> {
        val resultList = arrayListOf<AppData>()

        // Add locked apps first
        lockedApps.forEach { lockedAppEntity ->
            allApps.forEach { appData ->
                if (lockedAppEntity.packageName == appData.packageName) {
                    resultList.add(appData)
                }
            }
        }

        // Add the rest of the apps in alphabetical order
        val alphabeticOrderList: ArrayList<AppData> = arrayListOf()
        allApps.forEach { appData ->
            if (!resultList.contains(appData)) {
                alphabeticOrderList.add(appData)
            }
        }
        alphabeticOrderList.sortWith { app1, app2 -> app1.appName.compareTo(app2.appName) }
        resultList.addAll(alphabeticOrderList)

        return resultList
    }
}

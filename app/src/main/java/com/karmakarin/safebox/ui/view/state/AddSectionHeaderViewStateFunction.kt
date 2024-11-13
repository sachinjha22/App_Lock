package com.karmakarin.safebox.ui.view.state

import com.karmakarin.safebox.R

class AddSectionHeaderViewStateFunction {

    private val recommendedPackages = setOf(
        "com.whatsapp",
        "com.instagram.android",
        "com.facebook.orca",
        "com.facebook.katana",
        "com.google.android.apps.messaging",
        "org.telegram.messenger",
        "com.twitter.android",
        "com.google.android.apps.photos",
        "com.google.android.apps.docs"
    )

    suspend fun apply(appItemList: List<AppLockItemItemViewState>): List<AppLockItemBaseViewState> {
        // Hash for faster access
        val appListHash = appItemList.associateBy { it.appData.parsePackageName() }.toMutableMap()
        val resultList = mutableListOf<AppLockItemBaseViewState>()

        // Recommended header view state
        val recommendedHeaderViewState = AppLockItemHeaderViewState(R.string.header_recommended_to_lock)
        resultList.add(recommendedHeaderViewState)

        // Add recommended apps
        recommendedPackages.forEach { packageName ->
            appListHash[packageName]?.let { resultList.add(it) }
        }

        // All apps header view state
        val allAppsHeaderViewState = AppLockItemHeaderViewState(R.string.header_all_apps)
        resultList.add(allAppsHeaderViewState)

        // Add remaining apps that are not already in the result list
        appItemList.forEach { appItem ->
            if (!resultList.contains(appItem)) {
                resultList.add(appItem)
            }
        }

        return resultList
    }
}

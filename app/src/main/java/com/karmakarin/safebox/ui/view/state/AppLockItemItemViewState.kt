package com.karmakarin.safebox.ui.view.state

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.karmakarin.safebox.R
import com.karmakarin.safebox.db.lockedapps.AppData

data class AppLockItemItemViewState(val appData: AppData, var isLocked: Boolean = false) : AppLockItemBaseViewState() {

    fun appName() = appData.appName

    fun getLockIcon(context: Context): Drawable? {
        return if (isLocked) {
            ContextCompat.getDrawable(context, R.drawable.ic_lock_off)
        } else {
            ContextCompat.getDrawable(context, R.drawable.ic_lock_on)
        }
    }

    fun getAppIcon(): Drawable = appData.appIconDrawable

}
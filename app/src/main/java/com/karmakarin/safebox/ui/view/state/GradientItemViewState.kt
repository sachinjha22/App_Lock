package com.karmakarin.safebox.ui.view.state

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.karmakarin.safebox.util.interf.BackgroundItem

data class GradientItemViewState(
    val id: Int, @DrawableRes val gradientBackgroundRes: Int,
    var isChecked: Boolean = false
) : BackgroundItem {

    fun getGradientDrawable(context: Context): Drawable? {
        return ContextCompat.getDrawable(context, gradientBackgroundRes)
    }

    fun isCheckedVisible(): Int = if (isChecked) View.VISIBLE else View.INVISIBLE

}
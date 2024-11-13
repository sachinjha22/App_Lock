package com.karmakarin.safebox.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter

object ViewBinding {
    @JvmStatic
    @BindingAdapter("setImageDrawable")
    fun bindRecyclerView(
        view: ImageView,
        icon: Drawable?
    ) {
        try {
            view.setImageDrawable(icon)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
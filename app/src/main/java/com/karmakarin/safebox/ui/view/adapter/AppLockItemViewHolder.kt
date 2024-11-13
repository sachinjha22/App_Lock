package com.karmakarin.safebox.ui.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.karmakarin.safebox.R
import com.karmakarin.safebox.databinding.ItemAppLockListBinding
import com.karmakarin.safebox.ui.view.state.AppLockItemItemViewState

class AppLockItemViewHolder(
    private val binding: ItemAppLockListBinding,
    private val appItemClicked: ((AppLockItemItemViewState) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    init {
        try {
            binding.ivAppIcon.setOnClickListener {
                appItemClicked?.invoke(binding.viewState!!)
            }
        } catch (e: Exception) {
            Log.d("VVV", "error ${e.message}")
            e.printStackTrace()
        }
    }

    fun bind(appLockItemViewState: AppLockItemItemViewState) {
        binding.viewState = appLockItemViewState
        binding.executePendingBindings()
    }

    companion object {
        fun create(
            parent: ViewGroup,
            appItemClicked: ((AppLockItemItemViewState) -> Unit)?
        ): AppLockItemViewHolder {
            val binding = DataBindingUtil.inflate<ItemAppLockListBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_app_lock_list,
                parent,
                false
            )

            return AppLockItemViewHolder(binding, appItemClicked)
        }
    }
}
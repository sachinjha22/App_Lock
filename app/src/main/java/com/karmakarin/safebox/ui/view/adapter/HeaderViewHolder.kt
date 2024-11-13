package com.karmakarin.safebox.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.karmakarin.safebox.R
import com.karmakarin.safebox.databinding.ItemLockedListHeaderBinding
import com.karmakarin.safebox.ui.view.state.AppLockItemHeaderViewState

class HeaderViewHolder(private val binding: ItemLockedListHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(headerViewState: AppLockItemHeaderViewState) {
        binding.viewState = headerViewState
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): HeaderViewHolder {
            val binding = DataBindingUtil.inflate<ItemLockedListHeaderBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_locked_list_header,
                parent,
                false
            )

            return HeaderViewHolder(binding)
        }
    }

}
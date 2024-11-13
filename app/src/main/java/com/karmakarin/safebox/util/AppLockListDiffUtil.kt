package com.karmakarin.safebox.util

import androidx.recyclerview.widget.DiffUtil
import com.karmakarin.safebox.ui.view.state.AppLockItemBaseViewState
import com.karmakarin.safebox.ui.view.state.AppLockItemHeaderViewState
import com.karmakarin.safebox.ui.view.state.AppLockItemItemViewState

class AppLockListDiffUtil(
    private val oldList: List<AppLockItemBaseViewState>,
    private val newList: List<AppLockItemBaseViewState>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if ((oldItemPosition in oldList.indices).not() || (newItemPosition in newList.indices).not()) {
            return false
        }

        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem is AppLockItemItemViewState && newItem is AppLockItemItemViewState) {
            return oldItem.appData.packageName == newItem.appData.packageName
        }

        if (oldItem is AppLockItemHeaderViewState && newItem is AppLockItemHeaderViewState) {
            return oldItem.headerTextResource == newItem.headerTextResource
        }

        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem is AppLockItemItemViewState && newItem is AppLockItemItemViewState) {
            return oldItem.isLocked == newItem.isLocked
        }

        if (oldItem is AppLockItemHeaderViewState && newItem is AppLockItemHeaderViewState) {
            return oldItem.headerTextResource == newItem.headerTextResource
        }

        return false
    }

}
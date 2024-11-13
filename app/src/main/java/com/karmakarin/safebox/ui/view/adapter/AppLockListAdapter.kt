package com.karmakarin.safebox.ui.view.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.karmakarin.safebox.ui.view.state.AppLockItemBaseViewState
import com.karmakarin.safebox.ui.view.state.AppLockItemHeaderViewState
import com.karmakarin.safebox.ui.view.state.AppLockItemItemViewState
import com.karmakarin.safebox.util.AppLockListDiffUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppLockListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var appItemClicked: ((AppLockItemItemViewState) -> Unit)? = null

    private val itemViewStateList: ArrayList<AppLockItemBaseViewState> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setAppDataList(itemViewStateList: List<AppLockItemBaseViewState>, lifecycleScope: CoroutineScope) {
        lifecycleScope.launch {
            try {
                val diffResult = withContext(Dispatchers.Default) {
                    DiffUtil.calculateDiff(AppLockListDiffUtil(this@AppLockListAdapter.itemViewStateList, itemViewStateList))
                }

                this@AppLockListAdapter.itemViewStateList.clear()
                this@AppLockListAdapter.itemViewStateList.addAll(itemViewStateList)

                withContext(Dispatchers.Main) {
                    diffResult.dispatchUpdatesTo(this@AppLockListAdapter)
                }
            } catch (error: Exception) {
                // Handle error if needed, e.g., log to Bugsnag
//                Bugsnag.notify(error)

                this@AppLockListAdapter.itemViewStateList.clear()
                this@AppLockListAdapter.itemViewStateList.addAll(itemViewStateList)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = itemViewStateList.size

    override fun getItemViewType(position: Int): Int {
        return when (itemViewStateList[position]) {
            is AppLockItemHeaderViewState -> TYPE_HEADER
            is AppLockItemItemViewState -> TYPE_APP_ITEM
            else -> throw IllegalArgumentException("No type found")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_APP_ITEM -> AppLockItemViewHolder.create(parent, appItemClicked)
            TYPE_HEADER -> HeaderViewHolder.create(parent)
            else -> throw IllegalStateException("No type found")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AppLockItemViewHolder -> holder.bind(itemViewStateList[position] as AppLockItemItemViewState)
            is HeaderViewHolder -> holder.bind(itemViewStateList[position] as AppLockItemHeaderViewState)
        }
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_APP_ITEM = 1
    }
}

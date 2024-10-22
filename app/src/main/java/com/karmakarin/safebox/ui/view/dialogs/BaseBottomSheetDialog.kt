package com.karmakarin.safebox.ui.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.getDefaultComponent
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.karmakarin.safebox.util.annotation.BindingOnly

abstract class BaseBottomSheetDialog<T : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : BottomSheetDialogFragment() {

    private var bindingComponent: DataBindingComponent? = getDefaultComponent()

    private var _binding: T? = null

    @BindingOnly
    protected val binding: T
        get() = checkNotNull(_binding) {
            "BottomSheetDialogFragment $this binding cannot be accessed before onCreateView() or after onDestroyView()"
        }

    @BindingOnly
    protected inline fun binding(block: T.() -> Unit): T {
        return binding.apply(block)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, contentLayoutId, container, false, bindingComponent)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.unbind()
        _binding = null
    }
}
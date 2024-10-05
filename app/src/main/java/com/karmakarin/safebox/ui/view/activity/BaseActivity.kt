package com.karmakarin.safebox.ui.view.activity

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.getDefaultComponent
import androidx.databinding.ViewDataBinding
import com.karmakarin.safebox.util.annotation.BindingOnly

abstract class BaseActivity<T : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : AppCompatActivity() {

    private var bindingComponent: DataBindingComponent? = getDefaultComponent()

    @BindingOnly
    protected val binding: T by lazy(LazyThreadSafetyMode.NONE) {
        DataBindingUtil.setContentView(this, contentLayoutId, bindingComponent)
    }

    @BindingOnly
    protected inline fun binding(block: T.() -> Unit): T {
        return binding.apply(block)
    }

    init {
        addOnContextAvailableListener {
            binding.notifyChange()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}
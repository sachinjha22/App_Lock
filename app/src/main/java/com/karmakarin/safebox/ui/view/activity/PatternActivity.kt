package com.karmakarin.safebox.ui.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.karmakarin.safebox.R
import com.karmakarin.safebox.databinding.ActivityPatternBinding
import com.karmakarin.safebox.support.patterns.PatternListeners
import com.karmakarin.safebox.support.patterns.PatternLockView

class PatternActivity : BaseActivity<ActivityPatternBinding>(R.layout.activity_pattern) {

//    private val patternVM by viewModels<PatternViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        setUpUI()
    }

    private fun setUpUI() {
//        binding.ptView.addPatternLockListener(object : PatternListeners() {
//            override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
//                if (patternVM.isFirstPattern()) {
//                    patternVM.setFirstDrawnPattern(pattern)
//                } else {
//                    patternVM.setRedrawnPattern(pattern)
//                }
//                binding.ptView.clearPattern()
//            }
//        })
//        patternVM.getPatternEventLiveDataa.observe(this) { viewState ->
//            binding.viewState = viewState
//            binding.executePendingBindings()
//            if (viewState.isCreatedNewPattern()) {
//                onPatternCreateCompleted()
//            }
//        }
    }

    private fun onPatternCreateCompleted() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, PatternActivity::class.java)
        }
    }
}
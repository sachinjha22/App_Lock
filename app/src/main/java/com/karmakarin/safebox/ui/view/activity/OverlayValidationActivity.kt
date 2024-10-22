package com.karmakarin.safebox.ui.view.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.karmakarin.safebox.R
import com.karmakarin.safebox.databinding.ActivityOverlayValidationBinding
import com.karmakarin.safebox.support.camera.FrontPictureLiveData
import com.karmakarin.safebox.support.patterns.PatternListeners
import com.karmakarin.safebox.support.patterns.PatternLockView
import com.karmakarin.safebox.ui.view.viewModel.OverlayViewModel
import com.karmakarin.safebox.util.Constants.KEY_PACKAGE_NAME
import com.karmakarin.safebox.util.extensions.convertToPatternDot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverlayValidationActivity :
    BaseActivity<ActivityOverlayValidationBinding>(R.layout.activity_overlay_validation) {

    private val overlayVM by viewModels<OverlayViewModel>()
    private lateinit var frontPictureLiveData: FrontPictureLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        onBackPress
        intent.getStringExtra(KEY_PACKAGE_NAME)?.let { updateLaunchingAppIcon(it) }
        frontPictureLiveData =
            FrontPictureLiveData(application, overlayVM.getIntruderPictureImageFile())

        overlayVM.patternValidationViewStateLiveData.observe(this) {
            binding.patternLockView.clearPattern()
            binding.viewState = it
            binding.executePendingBindings()

            if (it.isDrawnCorrect == true || it.fingerPrintResultData?.isSucces() == true) {
                setResult(RESULT_OK)
                finish()
            }

            if (it.isDrawnCorrect == false || it.fingerPrintResultData?.isNotSucces() == true) {
//                YoYo.with(Techniques.Shake)
//                    .duration(700)
//                    .playOn(binding.textViewPrompt)
//
                if (it.isIntrudersCatcherMode) {
                    frontPictureLiveData.takePicture()
                }
            }
        }
        overlayVM.selectedBackgroundDrawableLiveData.observe(this) {
            binding.layoutOverlayMain.background = it.getGradientDrawable(this)
        }

        binding.patternLockView.addPatternLockListener(object : PatternListeners() {
            override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
                super.onComplete(pattern)
                pattern?.let { overlayVM.onPatternDrawn(it.convertToPatternDot()) }
            }
        })

    }

    private val onBackPress =
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                try {
                    if (supportFragmentManager.backStackEntryCount >= 1) {
                        supportFragmentManager.popBackStack()
                    } else {
                        val intent = Intent("android.intent.action.MAIN")
                        intent.addCategory("android.intent.category.HOME")
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

    private fun updateLaunchingAppIcon(appPackageName: String) {
        try {
            val icon = packageManager.getApplicationIcon(appPackageName)
            binding.avatarLock.setImageDrawable(icon)
        } catch (e: PackageManager.NameNotFoundException) {
            binding.avatarLock.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.mipmap.ic_launcher
                )
            )
            e.printStackTrace()
        }
    }

    companion object {
        fun newIntent(context: Context, packageName: String): Intent {
            val intent = Intent(context, OverlayValidationActivity::class.java)
            intent.putExtra(KEY_PACKAGE_NAME, packageName)
            return intent
        }
    }
}
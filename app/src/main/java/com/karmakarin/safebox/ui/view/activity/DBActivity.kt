package com.karmakarin.safebox.ui.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.karmakarin.safebox.R
import com.karmakarin.safebox.databinding.ActivityDashboardBinding
import com.karmakarin.safebox.ui.view.adapter.DashboardAdapter
import com.karmakarin.safebox.ui.view.dialogs.PrivacyPolicyDialog
import com.karmakarin.safebox.ui.view.viewModel.DashboardViewModel
import com.karmakarin.safebox.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DBActivity : BaseActivity<ActivityDashboardBinding>(R.layout.activity_dashboard),
    NavigationView.OnNavigationItemSelectedListener {

    private val dashboardVM by viewModels<DashboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding.lifecycleOwner = this
        setUpUI()
    }

    private fun setUpUI() {
        binding.dbView.ivViewMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.dbView.viewPager.adapter = DashboardAdapter(this, supportFragmentManager)
        binding.dbView.tablayout.setupWithViewPager(binding.dbView.viewPager)
        binding.navView.setNavigationItemSelectedListener(this)

        dashboardVM.getPatternCreationNeedLiveData.observe(this) { isPatternCreateNeed ->
            when {
                isPatternCreateNeed -> {
                    startActivityForResult(
                        PActivity.newIntent(this),
                        Constants.CREATE_PATTERN
                    )
                }

                dashboardVM.isAppLaunchValidated().not() -> {
                    startActivityForResult(
                        OVActivity.newIntent(this, this.packageName),
                        Constants.VALIDATE_PATTERN
                    )
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.CREATE_PATTERN -> {
                dashboardVM.onAppLaunchValidated()
                showPrivacyPolicyIfNeeded()
                if (resultCode != Activity.RESULT_OK) {
                    finish()
                }
            }

            Constants.VALIDATE_PATTERN -> {
                if (resultCode == Activity.RESULT_OK) {
                    dashboardVM.onAppLaunchValidated()
                    showPrivacyPolicyIfNeeded()
                } else {
                    finish()
                }
            }
        }
    }

    private fun showPrivacyPolicyIfNeeded() {
        if (dashboardVM.isPrivacyPolicyAccepted().not()) {
            PrivacyPolicyDialog.newInstance().show(supportFragmentManager, "Policy Screen")
        }
    }

}
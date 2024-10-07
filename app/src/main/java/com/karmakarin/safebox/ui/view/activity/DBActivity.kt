package com.karmakarin.safebox.ui.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.karmakarin.safebox.R
import com.karmakarin.safebox.databinding.ActivityDashboardBinding
import com.karmakarin.safebox.ui.view.adapter.DashboardAdapter
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
                        PatternActivity.newIntent(this),
                        Constants.CREATE_PATTERN
                    )
                }

                dashboardVM.isAppLaunchValidated().not() -> {
//                    startActivityForResult(
//                        OverlayValidationActivity.newIntent(this, this.packageName),
//                        Constants.VALIDATE_PATTERN
//                    )
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

}
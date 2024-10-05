package com.karmakarin.safebox.ui.view.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.GravityCompat
import com.karmakarin.safebox.R
import com.karmakarin.safebox.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DBActivity : BaseActivity<ActivityDashboardBinding>(R.layout.activity_dashboard) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding.lifecycleOwner = this
        binding.dbView.ivViewMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

}
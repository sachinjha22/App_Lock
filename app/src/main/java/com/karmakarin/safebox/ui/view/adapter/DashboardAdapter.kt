package com.karmakarin.safebox.ui.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.karmakarin.safebox.R
import com.karmakarin.safebox.ui.view.activity.DBActivity
import com.karmakarin.safebox.ui.view.fragments.SecurityFragment
import com.karmakarin.safebox.ui.view.fragments.SettingsFragment

class DashboardAdapter(
    private val dbActivity: DBActivity, fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    private val tabs = dbActivity.resources.getStringArray(R.array.tabs)

    override fun getItem(position: Int): Fragment {
        return when (position) {
            INDEX_SECURITY -> SecurityFragment.newInstance()
            INDEX_SETTINGS -> SettingsFragment.newInstance()
            else -> SecurityFragment.newInstance()
        }
    }

    override fun getCount(): Int = tabs.size

    override fun getPageTitle(position: Int): CharSequence? = tabs[position]

    companion object {

        private const val INDEX_SECURITY = 0
        private const val INDEX_SETTINGS = 1
    }
}
package com.karmakarin.safebox.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.karmakarin.safebox.R
import com.karmakarin.safebox.databinding.FragmentSecurityBinding
import com.karmakarin.safebox.support.recyclerannimation.MainActionsShowHideScrollListener
import com.karmakarin.safebox.ui.view.activity.DBActivity
import com.karmakarin.safebox.ui.view.adapter.AppLockListAdapter
import com.karmakarin.safebox.ui.view.dialogs.UsageAccessPermissionDialog
import com.karmakarin.safebox.ui.view.state.AppLockItemItemViewState
import com.karmakarin.safebox.ui.view.viewModel.SecurityViewModel
import com.karmakarin.safebox.util.PermissionChecker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecurityFragment : BaseFragment<FragmentSecurityBinding>(R.layout.fragment_security) {

    private lateinit var dbActivity: DBActivity
    private val adapter: AppLockListAdapter = AppLockListAdapter()
    private val securityVM by viewModels<SecurityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.lifecycleOwner = this
        dbActivity = activity as DBActivity
        setUpView()
        return binding.root
    }

    private fun setUpView() {
        binding.lifecycleOwner = this
        binding.rvAppList.adapter = adapter

        binding.rvAppList.addOnScrollListener(
            MainActionsShowHideScrollListener(
                binding.layoutMainActions.root,
                resources.getDimension(R.dimen.dp110) + resources.getDimension(R.dimen.dp16)
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        securityVM.getAppDataListLiveData().observe(viewLifecycleOwner) {
            adapter.setAppDataList(it, lifecycleScope)
        }

        adapter.appItemClicked = this@SecurityFragment::onAppSelected
    }

    private fun onAppSelected(selectedApp: AppLockItemItemViewState) {
        if (PermissionChecker.checkUsageAccessPermission(dbActivity).not()) {
            UsageAccessPermissionDialog.newInstance().show(dbActivity.supportFragmentManager, "")
        } else {
            if (selectedApp.isLocked) {
                securityVM.unlockApp(selectedApp)
            } else {
                securityVM.lockApp(selectedApp)
            }
        }
    }

    companion object {
        fun newInstance() = SecurityFragment()
    }
}
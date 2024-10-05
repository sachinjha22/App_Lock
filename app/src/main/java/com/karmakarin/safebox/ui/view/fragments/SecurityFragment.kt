package com.karmakarin.safebox.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karmakarin.safebox.R
import com.karmakarin.safebox.databinding.FragmentSecurityBinding

class SecurityFragment : BaseFragment<FragmentSecurityBinding>(R.layout.fragment_security) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }

    companion object {
        fun newInstance() = SecurityFragment()
    }
}
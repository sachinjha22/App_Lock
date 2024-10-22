package com.karmakarin.safebox.ui.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karmakarin.safebox.R
import com.karmakarin.safebox.databinding.PrivacyPolicyDialogBinding
import com.karmakarin.safebox.ui.view.viewModel.PrivacyPolicyViewModel
import com.karmakarin.safebox.util.extensions.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivacyPolicyDialog :
    BaseBottomSheetDialog<PrivacyPolicyDialogBinding>(R.layout.privacy_policy_dialog) {

    private val privacyPolicyVM by viewModels<PrivacyPolicyViewModel>()

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding.lifecycleOwner = this
//        binding.buttonAccept.setOnClickListener {
////            privacyPolicyVM.acceptPrivacyPolicy()
//            dismiss()
//        }
//
////        binding.textViewPrivacyPolicy.setOnClickListener {
////            startActivity(IntentHelper.privacyPolicyWebIntent())
////        }
//
//        return binding.root
//    }

    override fun onStart() {
        super.onStart()
        binding.lifecycleOwner = this
        binding.buttonAccept.setOnClickListener {
            privacyPolicyVM.acceptPrivacyPolicy()
            dismiss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let { sheet ->
                dialog.behavior.peekHeight = sheet.height
                sheet.parent.parent.requestLayout()
            }
        }
    }

    companion object {
        fun newInstance(): AppCompatDialogFragment {
            val dialog = PrivacyPolicyDialog()
            dialog.isCancelable = false
            return dialog
        }
    }

}
package com.karmakarin.safebox.ui.view.dialogs

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karmakarin.safebox.R
import com.karmakarin.safebox.databinding.PermissionDialogBinding

class UsageAccessPermissionDialog :
    BaseBottomSheetDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    override fun onStart() {
        super.onStart()
        binding.lifecycleOwner = this

        binding.buttonPermit.setOnClickListener {
            onPermitClicked()
        }

        binding.buttonCancel.setOnClickListener {
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

    private fun onPermitClicked() {
        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        dismiss()
    }

    companion object {

        fun newInstance(): AppCompatDialogFragment = UsageAccessPermissionDialog()
    }

}
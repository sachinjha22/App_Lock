package com.karmakarin.safebox.db.callblocker.addtoblacklist

//class AddToBlackListDialog : BaseBottomSheetDialog<AddToBlackListViewModel>() {
//
//    private val binding: DialogCallBlockerAddToBlacklistBinding by inflate(R.layout.dialog_call_blocker_add_to_blacklist)
//
//    override fun getViewModel(): Class<AddToBlackListViewModel> = AddToBlackListViewModel::class.java
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        binding.buttonBlock.setOnClickListener {
//            if (validateInputFields()) {
//                viewModel.blockNumber(editTextName.text.toString(), editTextPhoneNumber.text.toString())
//            }
//        }
//        binding.buttonCancel.setOnClickListener {
//            dismiss()
//        }
//
//        return binding.root
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//        viewModel.getViewStateLiveData()
//            .observe(this, Observer {
//                dismiss()
//            })
//    }
//
//    private fun validateInputFields(): Boolean {
//        return !binding.editTextPhoneNumber.text.isNullOrEmpty()
//    }
//
//    companion object {
//
//        fun newInstance(): AppCompatDialogFragment = AddToBlackListDialog()
//    }
//}
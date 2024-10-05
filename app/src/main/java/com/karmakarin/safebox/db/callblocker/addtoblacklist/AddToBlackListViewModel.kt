package com.karmakarin.safebox.db.callblocker.addtoblacklist

//class AddToBlackListViewModel @Inject constructor(val callBlockerRepository: CallBlockerRepository) :
//    RxAwareViewModel() {
//
//    private val addToBlacklistViewStateLiveData = MutableLiveData<AddToBlacklistViewState>()
//
//    fun blockNumber(name: String, phoneNumber: String) {
//        val userName = if (name.isNullOrEmpty()) DEFAULT_NAME else name
//        val blackListItemEntity = BlackListItemEntity(userName = userName, phoneNumber = phoneNumber)
//        disposables += callBlockerRepository.addToBlackList(blackListItemEntity)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { addToBlacklistViewStateLiveData.value = AddToBlacklistViewState(BlockState.BLOCKED) },
//                { t -> addToBlacklistViewStateLiveData.value = AddToBlacklistViewState(BlockState.ERROR) })
//    }
//
//    fun getViewStateLiveData(): LiveData<AddToBlacklistViewState> = addToBlacklistViewStateLiveData
//
//    companion object {
//
//        private const val DEFAULT_NAME = ""
//    }
//}
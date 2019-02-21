package com.thumbs.android.thumbsAndroid.ui.setting

import com.thumbs.android.thumbsAndroid.repositories.ThumbsRepository

class SettingPresenter(
    val thumbsRepository: ThumbsRepository
) : SettingContract.SettingUserActionListener {

    var settingView : SettingContract.SettingView?= null

    override fun load() {
        thumbsRepository.loadThumb(1).subscribe({
            settingView?.setUi(it)
        },{
            it.printStackTrace()
        })
    }

    override fun attachView(view: SettingContract.SettingView) {
        this.settingView = view
    }

    override fun removeView() {
        settingView = null
    }

}
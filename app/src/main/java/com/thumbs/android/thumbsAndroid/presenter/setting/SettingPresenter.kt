package com.thumbs.android.thumbsAndroid.presenter.setting

import android.util.Log
import com.thumbs.android.thumbsAndroid.repositories.UserRepository
import com.thumbs.android.thumbsAndroid.presenter.BasePresenter


class SettingPresenter(
    val userRepository: UserRepository
) : BasePresenter<SettingContract.SettingView>(), SettingContract.SettingUserActionListener{


    var settingView : SettingContract.SettingView?= null

    override fun load() {
        userRepository.getUser().subscribe ({
            Log.d("TEST","성공")
        },{
            Log.d("TEST","실패")
        })
    }

    override fun attachView(view: SettingContract.SettingView) {
        this.settingView = view
    }

    override fun removeView() {
        settingView = null
    }

}
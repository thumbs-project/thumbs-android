package com.thumbs.android.thumbsAndroid.presenter

import android.util.Log
import com.thumbs.android.thumbsAndroid.modules.domain.repositories.UserRepository
import com.thumbs.android.thumbsAndroid.presenter.BasePresenter
import com.thumbs.android.thumbsAndroid.presenter.contract.SettingContract


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
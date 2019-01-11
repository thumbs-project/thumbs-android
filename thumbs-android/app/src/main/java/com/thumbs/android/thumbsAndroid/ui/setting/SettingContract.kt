package com.thumbs.android.thumbsAndroid.ui.setting

import com.thumbs.android.thumbsAndroid.ui.base.BasePresenter
import com.thumbs.android.thumbsAndroid.ui.base.BaseView

interface SettingContract {
    interface SettingView : BaseView {
        fun setUi()
    }

    interface SettingUserActionListener : BasePresenter<SettingContract.SettingView> {
        fun load()
    }
}

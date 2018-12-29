package com.thumbs.android.thumbsAndroid.presenter.contract

import com.thumbs.android.thumbsAndroid.ui.base.BaseView



interface SettingContract{
    interface SettingView : BaseView {
        fun setUi()
    }

    interface SettingUserActionListener {
        fun load()
    }
}

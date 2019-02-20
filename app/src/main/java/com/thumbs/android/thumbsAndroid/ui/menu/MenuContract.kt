package com.thumbs.android.thumbsAndroid.ui.menu

import com.thumbs.android.thumbsAndroid.ui.base.BasePresenter

interface MenuContract {

    interface MenuView {
        fun actionListener(action: Int)
    }

    interface UserActionListerner : BasePresenter<MenuView> {
        fun postUserEvent()
    }
}
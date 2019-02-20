package com.thumbs.android.thumbsAndroid.ui.menu

import com.thumbs.android.thumbsAndroid.ui.base.BaseServicePresenter

interface MenuContract {

    interface MenuView {
        fun actionListener(action: Int)
    }

    interface UserActionListerner : BaseServicePresenter<MenuContract.MenuView> {
        fun postUserEvent(action: Int)
    }
}
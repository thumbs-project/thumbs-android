package com.thumbs.android.thumbsAndroid.ui.menu

import com.thumbs.android.thumbsAndroid.ui.base.BasePresenter

interface MenuContract {

    interface MenuView {
        fun setView(imageUrl : String)
        fun actionListener(action: Int)

    }

    interface UserActionListerner : BasePresenter<MenuContract.MenuView> {
        fun postUserEvent(action: Int)
    }
}
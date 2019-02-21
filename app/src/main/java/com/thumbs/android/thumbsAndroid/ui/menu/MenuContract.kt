package com.thumbs.android.thumbsAndroid.ui.menu

import com.thumbs.android.thumbsAndroid.ui.base.BasePresenter
import com.thumbs.android.thumbsAndroid.ui.status.StatusContract

interface MenuContract {

    interface MenuView {
        fun actionListener(action: Int)
    }

    interface UserActionListerner : BasePresenter<StatusContract.StatusView> {
        fun postUserEvent(action: Int)
    }
}
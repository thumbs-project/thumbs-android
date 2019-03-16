package com.thumbs.android.thumbsAndroid.ui.menu

import android.app.Service
import android.widget.ImageView
import com.thumbs.android.thumbsAndroid.ui.base.BasePresenter

interface MenuContract {

    interface MenuView {
        fun setView(imageUrl: String)
        fun actionListener(action: Int)
    }

    interface UserActionListerner : BasePresenter<MenuContract.MenuView> {
        fun postUserEvent(action: Int)
        fun getDefaultImageUrl(service:Service, image: ImageView)
    }
}
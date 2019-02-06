package com.thumbs.android.thumbsAndroid.ui.intro

import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.ui.base.BasePresenter

interface SplashContract {

    interface SplashView {
        fun success(thumbs: Thumb)
        fun fail()
    }

    interface SplashUserActionListerner : BasePresenter<SplashView> {
        fun loadThumbsData()
    }
}
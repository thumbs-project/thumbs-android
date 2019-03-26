package com.thumbs.android.thumbsAndroid.ui.splash

import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.ui.base.BasePresenter

interface SplashContract {

    interface SplashView {
        fun success(thumbs: Thumb)
        fun fail()
    }

    interface SplashUserActionListener : BasePresenter<SplashView> {
        fun loadThumbsData()
    }
}
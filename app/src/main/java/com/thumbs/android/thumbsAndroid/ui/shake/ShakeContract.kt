package com.thumbs.android.thumbsAndroid.ui.shake

import com.thumbs.android.thumbsAndroid.ui.base.BasePresenter

interface ShakeContract {
    interface ShakeEvent {
        fun onShake(count: Int)
    }

    interface ShakeEventListener : BasePresenter<ShakeEvent> {
        fun handleShakeEvent(count: Int)
    }
}
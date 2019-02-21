package com.thumbs.android.thumbsAndroid.ui.shake

import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.ui.base.BasePresenter
import com.thumbs.android.thumbsAndroid.ui.base.BaseView

interface ShakeContract {
    interface ShakeEvent{
        fun onShake(count: Int)
    }

    interface ShakeEventListener : BasePresenter<ShakeContract.ShakeEvent> {
        fun handleShakeEvent(count: Int)
    }
}

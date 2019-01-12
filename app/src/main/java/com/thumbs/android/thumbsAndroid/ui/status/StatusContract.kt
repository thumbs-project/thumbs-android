package com.thumbs.android.thumbsAndroid.ui.status

import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.ui.base.BasePresenter
import com.thumbs.android.thumbsAndroid.ui.base.BaseView

interface StatusContract {
    interface StatusView : BaseView {
        fun setUi(thumb: Thumb)
    }

    interface StatusUserActionListener : BasePresenter<StatusContract.StatusView> {
        fun loadThumb()
    }
}

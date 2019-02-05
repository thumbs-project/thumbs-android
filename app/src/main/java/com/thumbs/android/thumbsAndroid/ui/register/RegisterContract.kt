package com.thumbs.android.thumbsAndroid.ui.register

import com.thumbs.android.thumbsAndroid.ui.base.BasePresenter
import com.thumbs.android.thumbsAndroid.ui.base.BaseView

interface RegisterContract {
    interface RegisterView : BaseView {
        fun nextPage()
        fun startService()
        fun isNotEmptyName(): Boolean
        fun createThumbsFail()
    }

    interface RegisterUserActionListener : BasePresenter<RegisterContract.RegisterView> {
        fun createThumb(thumbName: String)
    }
}

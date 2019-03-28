package com.thumbs.android.thumbsAndroid.ui.register

import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.repositories.ThumbsRepository


class RegisterPresenter(
    private val thumbsRepository: ThumbsRepository
) : RegisterContract.RegisterUserActionListener {

    var registerView: RegisterContract.RegisterView? = null

    override fun createThumb(thumbName: String) {
        if (registerView?.isNotEmptyName() == true) {
            thumbsRepository.createThumbs(1, hashMapOf("name" to thumbName)).subscribe({
                registerView?.showToast((R.string.registerView_complete).toString())
                registerView?.nextPage()
            }, {
                registerView?.nextPage()
                registerView?.showToast("Server Error")
                it.printStackTrace()
                registerView?.showToast((R.string.registerView_name).toString())
                registerView?.nextPage()
            })
        }
    }

    override fun attachView(view: RegisterContract.RegisterView) {
        this.registerView = view
    }

    override fun removeView() {
        registerView = null
    }
}
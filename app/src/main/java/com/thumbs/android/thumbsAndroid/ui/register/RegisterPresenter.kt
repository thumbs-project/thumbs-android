package com.thumbs.android.thumbsAndroid.ui.register

import com.thumbs.android.thumbsAndroid.repositories.ThumbsRepository


class RegisterPresenter(
    val thumbsRepository: ThumbsRepository
) : RegisterContract.RegisterUserActionListener {

    var registerView: RegisterContract.RegisterView? = null


    override fun createThumb(thumbName: String) {
        if (registerView?.isNotEmptyName() == true) {
            thumbsRepository.createThumbs(1, hashMapOf("name" to thumbName)).subscribe({
                registerView?.showToast("등록이 완료되었습니다.")
                registerView?.nextPage()
                registerView?.startService()
            }, {
                registerView?.createThumbsFail()
                it.printStackTrace()
            })
        } else
            registerView?.showToast("이름을 입력해주세요!")
    }

    override fun attachView(view: RegisterContract.RegisterView) {
        this.registerView = view
    }

    override fun removeView() {
        registerView = null
    }

}
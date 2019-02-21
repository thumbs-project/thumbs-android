package com.thumbs.android.thumbsAndroid.ui.splash

import com.thumbs.android.thumbsAndroid.repositories.ThumbsRepository

class SplashPresenter(
    val thumbsRepository: ThumbsRepository
) : SplashContract.SplashUserActionListerner {

    var splashView: SplashContract.SplashView? = null

    override fun attachView(view: SplashContract.SplashView) {
        splashView = view
    }

    override fun loadThumbsData() {
        //TODO how to deal with when having multiple Thumbs?
        thumbsRepository.loadThumb(1)
            .subscribe({
                splashView?.success(it)
            }, {
                splashView?.fail()
            })
    }


    override fun removeView() {
        splashView = null
    }

}

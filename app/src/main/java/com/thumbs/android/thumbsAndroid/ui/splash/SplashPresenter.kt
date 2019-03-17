package com.thumbs.android.thumbsAndroid.ui.splash

import com.thumbs.android.thumbsAndroid.repositories.ThumbsRepository
import io.reactivex.schedulers.Schedulers.*

class SplashPresenter(
    val thumbsRepository: ThumbsRepository
) : SplashContract.SplashUserActionListener {

    var splashView: SplashContract.SplashView? = null

    override fun attachView(view: SplashContract.SplashView) {
        splashView = view
    }

    override fun loadThumbsData() {
        //TODO how to deal with when having multiple Thumbs?
        thumbsRepository.loadThumb(1)
            .observeOn(io())
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

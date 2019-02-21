package com.thumbs.android.thumbsAndroid.ui.shake

import com.thumbs.android.thumbsAndroid.repositories.ThumbsRepository
import com.thumbs.android.thumbsAndroid.ui.intro.SplashContract

class ShakePresenter(
    val thumbsRepository: ThumbsRepository
) : ShakeContract.ShakeEventListener {

    var shakeEvent: ShakeContract.ShakeEvent? = null

    override fun handleShakeEvent(count: Int){

    }

    override fun attachView(event: ShakeContract.ShakeEvent) {
        shakeEvent = event
    }

    override fun removeView() {
        shakeEvent = null
    }

}

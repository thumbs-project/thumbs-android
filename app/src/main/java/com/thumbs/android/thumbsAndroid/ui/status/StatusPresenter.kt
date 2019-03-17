package com.thumbs.android.thumbsAndroid.ui.status

import com.thumbs.android.thumbsAndroid.repositories.ThumbsRepository

class StatusPresenter(
    val thumbsRepository: ThumbsRepository
) : StatusContract.StatusUserActionListener {


    var statusView: StatusContract.StatusView? = null

    override fun loadThumb() {
        thumbsRepository.loadThumb(1)
            .subscribe({
                statusView?.setUi(it)
                statusView?.loadSuccess()
            }, {
                statusView?.loadFail()
                it.printStackTrace()
            })
    }

    override fun attachView(view: StatusContract.StatusView) {
        this.statusView = view
    }

    override fun removeView() {
        statusView = null
    }

}
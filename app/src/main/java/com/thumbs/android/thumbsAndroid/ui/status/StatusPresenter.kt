package com.thumbs.android.thumbsAndroid.ui.status

import android.util.Log
import com.thumbs.android.thumbsAndroid.repositories.ThumbsRepository
import com.thumbs.android.thumbsAndroid.repositories.UserRepository
import com.thumbs.android.thumbsAndroid.ui.base.BasePresenter

class StatusPresenter(
    val thumbsRepository: ThumbsRepository
) : StatusContract.StatusUserActionListener {



    var statusView : StatusContract.StatusView ?= null

    override fun loadThumb() {
        thumbsRepository.loadThumb(1).subscribe({
            statusView?.setUi(it)
            Log.d("status_it",it.toString())
        },{
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
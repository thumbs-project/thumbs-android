package com.thumbs.android.thumbsAndroid.ui.setting

import android.util.Log
import com.thumbs.android.thumbsAndroid.model.ThumbSize
import com.thumbs.android.thumbsAndroid.repositories.ThumbsRepository
import io.reactivex.subjects.PublishSubject
import org.koin.core.parameter.parametersOf

class SettingPresenter(
    val thumbsRepository: ThumbsRepository
) : SettingContract.SettingUserActionListener {

    var settingView: SettingContract.SettingView? = null
    var MIN_HEIGHT_SIZE = 223
    var MIN_WIDTH_SIZE = 184
    var STEP = 3

    override fun load() {
        thumbsRepository.loadThumb(1).subscribe({
            settingView?.setUi(it)
        }, {
            it.printStackTrace()
        })
    }

    override fun attachView(view: SettingContract.SettingView) {
        this.settingView = view
    }

    override fun removeView() {
        settingView = null
    }

    override fun controlThumbSize(imageSize: ThumbSize, progress: Int) {
        //확장성있고 더 우아하게 만드는 방법이 있을수도..??
        settingView?.setImageSize(
            ThumbSize(
                MIN_WIDTH_SIZE + (STEP * progress),
                MIN_HEIGHT_SIZE + (STEP * progress)
            )
        )
    }
}
package com.thumbs.android.thumbsAndroid.ui.setting

import android.util.Log
import com.thumbs.android.thumbsAndroid.data.cache.toCached
import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.model.ThumbSize
import com.thumbs.android.thumbsAndroid.repositories.ThumbsRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import org.koin.core.parameter.parametersOf

class SettingPresenter(
    val thumbsRepository: ThumbsRepository
) : SettingContract.SettingUserActionListener {

    var settingView: SettingContract.SettingView? = null

    companion object {
        var MIN_HEIGHT_SIZE = 255
        var MIN_WIDTH_SIZE = 210
        var STEP = 3
    }

    override fun load() {
        Single.zip(thumbsRepository.loadThumb(1), thumbsRepository.selectThumbSize(),
            BiFunction<Thumb, ThumbSize, Pair<Thumb, ThumbSize>> { t1, t2 ->
                return@BiFunction (t1 to t2)
            }).subscribe({
            settingView?.setUi(it.first, it.second)
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

    override fun upsert(size: ThumbSize) {
        thumbsRepository.insertThumbSize(size.toCached()).subscribe({
            Log.d("test_upsert", "성공!")
        }, {
            it.printStackTrace()
        })
    }

}
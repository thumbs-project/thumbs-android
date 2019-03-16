package com.thumbs.android.thumbsAndroid.repositories

import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.model.UserAction
import io.reactivex.Single

interface UserEventRepository {
    fun sendEvent(thumbId : Int, body : UserAction) : Single<Thumb>
    fun getThumbsStatus(thumbsId:Int): Single<Thumb>
}
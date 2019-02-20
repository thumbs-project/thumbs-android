package com.thumbs.android.thumbsAndroid.repositories

import com.thumbs.android.thumbsAndroid.api.UserEventApi
import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.model.UserAction
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class UserEventRepositoryImpl(val userEventApi: UserEventApi) : UserEventRepository {
    override fun sendEvent(thumbId: Int, body: UserAction?): Single<Thumb> {
        return userEventApi.sendEvent(thumbId, body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

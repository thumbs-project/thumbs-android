

package com.thumbs.android.thumbsAndroid.repositories

import com.thumbs.android.thumbsAndroid.api.ThumbsApi
import com.thumbs.android.thumbsAndroid.model.Thumb
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ThumbsRepositoryImpl(val thumbApi: ThumbsApi) : ThumbsRepository{
    override fun createThumbs(userId: Int, body: HashMap<String, Any>): Completable {
        return thumbApi.createThumbs(userId, body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())    }

    override fun loadThumb(thumbId: Int): Single<Thumb> {
        return thumbApi.loadThumb(thumbId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}
package com.thumbs.android.thumbsAndroid.repositories

import com.thumbs.android.thumbsAndroid.api.ThumbsApi
import com.thumbs.android.thumbsAndroid.data.cache.CacheThumbSize
import com.thumbs.android.thumbsAndroid.data.cache.toCached
import com.thumbs.android.thumbsAndroid.data.cache.unCached
import com.thumbs.android.thumbsAndroid.data.dao.ThumbDao
import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.model.ThumbSize
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ThumbsRepositoryImpl(
    val thumbApi: ThumbsApi,
    val dao: ThumbDao
) : ThumbsRepository {


    override fun createThumbs(userId: Int, body: HashMap<String, Any>): Completable {
        return thumbApi.createThumbs(userId, body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadThumb(thumbId: Int): Single<Thumb> {
        return thumbApi.loadThumb(thumbId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertThumbSize(thumbSize: CacheThumbSize): Completable {
        return Completable.fromAction {
            dao.upsert(thumbSize)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun selectThumbSize(): Single<ThumbSize> {
        return Single.just(dao).observeOn(Schedulers.io())
            .map { it -> dao.selectThumbSize().unCached() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
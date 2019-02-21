package com.thumbs.android.thumbsAndroid.repositories

import com.thumbs.android.thumbsAndroid.data.cache.CacheThumbSize
import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.model.ThumbSize
import io.reactivex.Completable
import io.reactivex.Single


interface ThumbsRepository{

    fun createThumbs(userId : Int, body :  HashMap<String, Any>) : Completable

    fun loadThumb(thumbId : Int) : Single<Thumb>

    fun insertThumbSize(thumbSize: CacheThumbSize) : Completable

    fun selectThumbSize() : Single<ThumbSize>
}
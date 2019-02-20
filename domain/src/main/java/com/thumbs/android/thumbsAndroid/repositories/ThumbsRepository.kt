package com.thumbs.android.thumbsAndroid.repositories

import com.thumbs.android.thumbsAndroid.model.Thumb
import io.reactivex.Completable
import io.reactivex.Single


interface ThumbsRepository{

    fun createThumbs(userId : Int, body :  HashMap<String, Any>) : Completable
    fun loadThumb(thumbId : Int) : Single<Thumb>
}
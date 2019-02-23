package com.thumbs.android.thumbsAndroid.api

import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.model.UserAction
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserEventApi {
    @POST("/thumbs/{thumbId}/event")
    fun sendEvent(
        @Path("thumbId") thumbId: Int,
        @Body userAction: UserAction
    ): Single<Thumb>

    @GET("/thumbs/{thumbId}")
    fun loadThumb(
        @Path("thumbId") thumbId: Int
    ): Single<Thumb>

}
package com.thumbs.android.thumbsAndroid.modules.network.api

import com.thumbs.android.thumbsAndroid.modules.domain.model.User
import io.reactivex.Single
import retrofit2.http.GET

interface UserApi {
    @GET("/get")
    fun getUsers() : Single<User>
}
package com.thumbs.android.thumbsAndroid.modules.network.api

import com.thumbs.android.thumbsAndroid.modules.domain.model.Status
import com.thumbs.android.thumbsAndroid.modules.domain.model.StatusRequestParam
import com.thumbs.android.thumbsAndroid.modules.domain.model.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
  @POST("/")
  fun getStatus(
    @Body
    statusRequestParam: StatusRequestParam
  ): Single<Status>

  @GET("/get")
  fun getUsers(): Single<User>
}
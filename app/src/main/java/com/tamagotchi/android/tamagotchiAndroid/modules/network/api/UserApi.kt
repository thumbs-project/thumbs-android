package com.tamagotchi.android.tamagotchiAndroid.modules.network.api

import com.tamagotchi.android.tamagotchiAndroid.modules.domain.model.User
import io.reactivex.Single
import retrofit2.http.GET

interface UserApi {
    @GET("/get")
    fun getUsers() : Single<User>
}
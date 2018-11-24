package com.thumbs.android.thumbsAndroid.modules.domain.repositories

import com.thumbs.android.thumbsAndroid.modules.domain.model.User
import com.thumbs.android.thumbsAndroid.modules.network.api.UserApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class UserRepositoryImpl(val userApi: UserApi) : UserRepository {
  override fun getUser(): Single<User> = userApi.getUsers()
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
}
package com.thumbs.android.thumbsAndroid.repositories

import com.thumbs.android.thumbsAndroid.model.Status
import com.thumbs.android.thumbsAndroid.model.StatusRequestParam
import com.thumbs.android.thumbsAndroid.model.User
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class UserRepositoryImpl(val userApi: UserApi) : UserRepository {
  override fun getUser(): Single<User> = userApi.getUsers()
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

  override fun getStatus(
    statusRequestParam: StatusRequestParam
  ): Single<Status> {
    return userApi.getStatus(statusRequestParam)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }
}


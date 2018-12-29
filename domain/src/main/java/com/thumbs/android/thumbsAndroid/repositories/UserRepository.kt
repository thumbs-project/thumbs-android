package com.thumbs.android.thumbsAndroid.repositories

import com.thumbs.android.thumbsAndroid.model.Status
import com.thumbs.android.thumbsAndroid.model.StatusRequestParam
import com.thumbs.android.thumbsAndroid.model.User
import io.reactivex.Single

interface UserRepository {
  fun getUser() : Single<User>
  fun getStatus(
    statusRequestParam: StatusRequestParam
  ) : Single<Status>
}

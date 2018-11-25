package com.thumbs.android.thumbsAndroid.modules.domain.repositories

import com.thumbs.android.thumbsAndroid.modules.domain.model.Status
import com.thumbs.android.thumbsAndroid.modules.domain.model.StatusRequestParam
import com.thumbs.android.thumbsAndroid.modules.domain.model.User
import io.reactivex.Single

interface UserRepository {
  fun getUser() : Single<User>
  fun getStatus(
    statusRequestParam: StatusRequestParam
  ) : Single<Status>
}

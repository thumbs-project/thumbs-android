package com.tamagotchi.android.tamagotchiAndroid.modules.domain.repositories

import com.tamagotchi.android.tamagotchiAndroid.modules.domain.model.User
import io.reactivex.Single


interface UserRepository{
  fun getUser() : Single<User>
}
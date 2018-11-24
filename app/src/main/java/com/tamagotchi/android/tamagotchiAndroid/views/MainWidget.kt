package com.tamagotchi.android.tamagotchiAndroid.views

import android.app.Service
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import com.tamagotchi.android.tamagotchiAndroid.R
import com.tamagotchi.android.tamagotchiAndroid.modules.domain.repositories.UserRepositoryImpl
import com.tamagotchi.android.tamagotchiAndroid.modules.domain.repositories.UserRepository
import com.tamagotchi.android.tamagotchiAndroid.modules.network.NetworkConnector
import com.tamagotchi.android.tamagotchiAndroid.modules.network.api.UserApi

class MainWidget {
  var singleTabConfirm: GestureDetector? = null
  var btnClose : ImageView? = null
  val userRepository : UserRepository by lazy { UserRepositoryImpl(NetworkConnector.createRetrofit(UserApi::class.java)) }

  constructor(service: Service, windowManager: WindowManager) {
    singleTabConfirm = GestureDetector(service, SingleTapConfirm());

    val view = LayoutInflater.from(service)
      .inflate(R.layout.layout_floating_widget, null)

    val layoutParams = createLayoutParams(-100, -100)

    windowManager.addView(view, layoutParams);

    btnClose = view.findViewById(R.id.buttonClose)
    btnClose?.setOnClickListener {
      userRepository.getUser().subscribe({
        Log.d("TAG_NETWORK","success")
        Toast.makeText(service, it.url, Toast.LENGTH_SHORT)
          .show()
      }, {
        Log.d("TAG_NETWORK","error")
      })
    }

    setOnTouch(
      view,
      layoutParams, singleTabConfirm!!,
      windowManager)
  }
}

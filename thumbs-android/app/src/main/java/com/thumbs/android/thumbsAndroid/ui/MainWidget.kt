package com.thumbs.android.thumbsAndroid.ui

import android.app.Service
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.core.network.NetworkConnector
import com.thumbs.android.thumbsAndroid.core.network.api.UserApi
import com.thumbs.android.thumbsAndroid.core.repositories.UserRepository
import com.thumbs.android.thumbsAndroid.core.repositories.UserRepositoryImpl

class MainWidget {
  var singleTabConfirm: GestureDetector? = null
  val userRepository : UserRepository by lazy {
    UserRepositoryImpl(NetworkConnector.createRetrofit(UserApi::class.java))
  }
  var layoutParams = createLayoutParams(-100, -100)

  constructor(service: Service, windowManager: WindowManager) {
    singleTabConfirm = GestureDetector(service, SingleTapConfirm());

    val view = LayoutInflater.from(service)
      .inflate(R.layout.layout_floating_widget, null)

    val image = view.findViewById<ImageView>(R.id.icon_thu)
    image.setBackgroundResource(R.drawable.thu_cat)

    windowManager.addView(view, layoutParams);

    val action = Action(service, windowManager, layoutParams)

    setOnTouch(
      action,
      view,
      layoutParams,
      singleTabConfirm!!,
      windowManager,
      this::handleSingleClick
    )


  }

  fun handleSingleClick(view: View) {
   /* userRepository.getStatus(StatusRequestParam(
      123412341234,
      "wash"
    )).subscribe({ result ->
        Toast.makeText(
          view.context,
          result.property + " " + result.request_id,
          Toast.LENGTH_SHORT
        )
          .show()
      }, { throwable ->
        throwable.printStackTrace()
      })*/

  }
}

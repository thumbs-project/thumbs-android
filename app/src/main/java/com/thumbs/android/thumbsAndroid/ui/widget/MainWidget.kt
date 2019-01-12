package com.thumbs.android.thumbsAndroid.ui.widget

import android.app.Service
import android.view.*
import android.widget.ImageView
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.ui.menu.Action

class MainWidget {
  var singleTabConfirm: GestureDetector? = null
 /* val userRepository : UserRepository by lazy {
    UserRepositoryImpl(NetworkConnector.createRetrofit(UserApi::class.java))
  }*/

  constructor(service: Service, windowManager: WindowManager) {
    singleTabConfirm = GestureDetector(service, SingleTapConfirm());

    val view = LayoutInflater.from(service)
      .inflate(R.layout.layout_floating_widget, null)

    val layoutParams = createLayoutParams(-100, -100)
    val image = view.findViewById<ImageView>(R.id.icon_thu)
    image.setBackgroundResource(R.drawable.thu_basic)

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

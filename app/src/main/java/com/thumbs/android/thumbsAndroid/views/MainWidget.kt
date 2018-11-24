package com.thumbs.android.thumbsAndroid.views

import android.app.Service
import android.view.*
import android.widget.ImageView
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.modules.domain.repositories.UserRepositoryImpl
import com.thumbs.android.thumbsAndroid.modules.domain.repositories.UserRepository
import com.thumbs.android.thumbsAndroid.modules.network.NetworkConnector
import com.thumbs.android.thumbsAndroid.modules.network.api.UserApi

class MainWidget {
  var singleTabConfirm: GestureDetector? = null
  var btnClose : ImageView? = null
  val userRepository : UserRepository by lazy { UserRepositoryImpl(NetworkConnector.createRetrofit(UserApi::class.java)) }

  constructor(service: Service, windowManager: WindowManager) {
    singleTabConfirm = GestureDetector(service, SingleTapConfirm());

    val view = LayoutInflater.from(service)
      .inflate(R.layout.layout_floating_widget, null)

    val layoutParams = createLayoutParams(-100, -100)
    val image = view.findViewById<ImageView>(R.id.icon_thu)
    image.setBackgroundResource(R.drawable.thu_cat)

    windowManager.addView(view, layoutParams);

   /* btnClose?.setOnClickListener {
      userRepository.getUser().subscribe({
        Log.d("TAG_NETWORK","success")
        Toast.makeText(service, it.url, Toast.LENGTH_SHORT)
          .show()
      }, {
        Log.d("TAG_NETWORK","error")
      })
    }*/

    setOnTouch(
      view,
      layoutParams, singleTabConfirm!!,
      windowManager)
  }
}

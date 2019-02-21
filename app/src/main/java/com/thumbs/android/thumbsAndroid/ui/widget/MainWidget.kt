package com.thumbs.android.thumbsAndroid.ui.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Service
import android.view.*
import android.view.animation.Animation
import android.widget.ImageView
import com.daimajia.easing.Glider
import com.daimajia.easing.Skill
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.ui.menu.Menu
import kotlinx.android.synthetic.main.activity_splash.*

class MainWidget {
  var singleTabConfirm: GestureDetector? = null
 /* val userRepository : UserRepository by lazy {
    UserRepositoryImpl(NetworkConnector.createRetrofit(UserApi::class.java))
  }*/

  constructor(service: Service, windowManager: WindowManager) {
    singleTabConfirm = GestureDetector(service, SingleTapConfirm())

    val view = LayoutInflater.from(service)
      .inflate(R.layout.layout_floating_widget, null)

    val layoutParams = createLayoutParams(0, -310)
    val image = view.findViewById<ImageView>(R.id.icon_thu)
    image.setBackgroundResource(R.drawable.thu_basic)

    windowManager.addView(view, layoutParams)

      ObjectAnimator.ofFloat(50f, -70f).apply {
          addUpdateListener {
              it.duration=700
              it.repeatCount=ValueAnimator.INFINITE
              it.repeatMode = ValueAnimator.REVERSE
              //  it.repeatMode=ValueAnimator.RESTART
              view.translationY = it.animatedValue as Float
              windowManager.updateViewLayout(view, layoutParams)
          }
      }.start()


      val menu = Menu(service, windowManager, layoutParams)

      setOnTouch(
          menu,
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

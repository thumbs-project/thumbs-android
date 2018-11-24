package com.tamagotchi.android.tamagotchiAndroid.views

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Service
import android.view.*
import com.tamagotchi.android.tamagotchiAndroid.R
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout

val VIEWPORT_WIDTH_MAX: Int = 1000

class UpdownWidget {
  var singleTabConfirm: GestureDetector? = null;

  constructor(service: Service, windowManager: WindowManager) {
    singleTabConfirm = GestureDetector(service, SingleTapConfirm());

    val view = LayoutInflater.from(service)
      .inflate(R.layout.layout_floating_widget, null)


    val image = view.findViewById<ImageView>(R.id.icon_thu)
    image.setBackgroundResource(R.drawable.thu_un)

    val layoutParams = createLayoutParams(VIEWPORT_WIDTH_MAX, 0)

    windowManager.addView(view, layoutParams);

    setOnTouch(view, layoutParams, singleTabConfirm!!, windowManager)

    animateUpdown(view , layoutParams, windowManager)
  }
}

fun animateUpdown(view: View, layoutParams: WindowManager.LayoutParams, windowManager: WindowManager) {
  val initialY = layoutParams.y
  val animator = ValueAnimator.ofInt(-50, 50).apply {
    this.duration = 2000
    this.interpolator = AccelerateDecelerateInterpolator()
    this.repeatCount = ObjectAnimator.INFINITE
    this.repeatMode = ObjectAnimator.REVERSE
    this.addUpdateListener {
      val value = it.animatedValue
      layoutParams.y = initialY + (value as Int + 1) * 2
      windowManager.updateViewLayout(view, layoutParams)
    }
  }
  animator.start()
}
package com.tamagotchi.android.tamagotchiAndroid.views

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Service
import android.util.Log
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import com.tamagotchi.android.tamagotchiAndroid.R

class ItemWidget {
  var singleTabConfirm: GestureDetector? = null

  constructor(service: Service, windowManager: WindowManager) {
    singleTabConfirm = GestureDetector(service, SingleTapConfirm());

    var view = LayoutInflater.from(service)
      .inflate(R.layout.layout_floating_widget, null)

    var layoutParams = createLayoutParams(0, 250);

    windowManager.addView(view, layoutParams);

    setOnTouch(view, layoutParams, singleTabConfirm!!, windowManager)

    animateShowHide(view, layoutParams, windowManager)
  }
}

fun animateShowHide(view: View, layoutParams: WindowManager.LayoutParams, windowManager: WindowManager) {
  val animator = ValueAnimator.ofInt(-1, 1).apply {
    this.duration = 2000
    this.interpolator = AccelerateDecelerateInterpolator()
    this.repeatCount = ObjectAnimator.INFINITE
    this.repeatMode = ObjectAnimator.REVERSE
    this.addUpdateListener {
      val value = it.animatedFraction
      layoutParams.alpha = value
      windowManager.updateViewLayout(view, layoutParams)
    }
  }
  animator.start()
}

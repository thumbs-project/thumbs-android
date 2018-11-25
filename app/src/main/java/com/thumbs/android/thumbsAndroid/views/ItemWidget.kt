package com.thumbs.android.thumbsAndroid.views

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Service
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import com.thumbs.android.thumbsAndroid.R

class ItemWidget {
  var singleTabConfirm: GestureDetector? = null

  constructor(service: Service, windowManager: WindowManager) {
    singleTabConfirm = GestureDetector(service, SingleTapConfirm());

    var view = LayoutInflater.from(service)
      .inflate(R.layout.layout_floating_widget, null)

    val image = view.findViewById<ImageView>(R.id.icon_thu)
    image.setBackgroundResource(R.drawable.thu_music)


    var layoutParams = createLayoutParams(0, 250);

    windowManager.addView(view, layoutParams);

    setOnTouch(view, layoutParams, singleTabConfirm!!, windowManager, null)

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

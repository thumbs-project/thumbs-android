package com.tamagotchi.android.tamagotchiAndroid.views

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Service
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener

import kotlinx.android.synthetic.main.layout_floating_widget.view.*

import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation

fun createLayoutParams(
  posX: Int,
  posY: Int
): WindowManager.LayoutParams {
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
    return WindowManager.LayoutParams(
      WindowManager.LayoutParams.WRAP_CONTENT,
      WindowManager.LayoutParams.WRAP_CONTENT,
      posX,
      posY,
      WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
      WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
      PixelFormat.TRANSLUCENT
    )
  } else {
    return WindowManager.LayoutParams(
      WindowManager.LayoutParams.WRAP_CONTENT,
      WindowManager.LayoutParams.WRAP_CONTENT,
      posX,
      posY,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
      WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
      PixelFormat.TRANSLUCENT
    )
  }
}

fun setOnTouch(
  view: View,
  layoutParams: WindowManager.LayoutParams,
  singleTabConfirm: GestureDetector,
  windowManager: WindowManager
) {
  view.setOnTouchListener(object: View.OnTouchListener {
    private var initialX: Int = 0
    private var initialY: Int = 0
    private var initialTouchX: Float = 0.toFloat()
    private var initialTouchY: Float = 0.toFloat()

    override fun onTouch(v: View, event: MotionEvent): Boolean {
      if (singleTabConfirm?.onTouchEvent(event)?: false) {
        // single tab
        Log.d("floatingView", "widget clicked")
        return true
      } else {
        when (event.action) {
          MotionEvent.ACTION_DOWN -> {
            initialX = layoutParams.x
            initialY = layoutParams.y
            initialTouchX = event.rawX
            initialTouchY = event.rawY
            return true
          }

          MotionEvent.ACTION_UP -> {
            //when the drag is ended switching the state of the widget
            //hideLayout()
            //return true
          }

          MotionEvent.ACTION_MOVE -> {
            //this code is helping the widget to move around the screen with fingers
            layoutParams.x = initialX + (event.rawX - initialTouchX).toInt()
            layoutParams.y = initialY + (event.rawY - initialTouchY).toInt()
            windowManager.updateViewLayout(view, layoutParams)
            return true
          }
        }
      }
      return false
    }
  })
}


class SingleTapConfirm : SimpleOnGestureListener() {
  override fun onSingleTapUp(event: MotionEvent): Boolean {
    return true
  }
}

package com.thumbs.android.thumbsAndroid.views

import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener

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

fun qsetOnTouch(
  view: View,
  layoutParams: WindowManager.LayoutParams,
  singleTabConfirm: GestureDetector,
  windowManager: WindowManager,
  handleClickSingle: ((view: View) -> Unit)?
) {
  view.setOnTouchListener(object: View.OnTouchListener {
    private var initialX: Int = 0
    private var initialY: Int = 0
    private var initialTouchX: Float = 0.toFloat()
    private var initialTouchY: Float = 0.toFloat()

    override fun onTouch(v: View, event: MotionEvent): Boolean {
      if (singleTabConfirm?.onTouchEvent(event)?: false) {
        Log.d("floatingView", "widget clicked")

        if (handleClickSingle != null) {
          handleClickSingle(view)
        }

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

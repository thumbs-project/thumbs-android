package com.thumbs.android.thumbsAndroid.ui.widget

import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.GestureDetector.SimpleOnGestureListener
import com.thumbs.android.thumbsAndroid.ui.menu.Menu
import android.animation.ValueAnimator
import android.animation.PropertyValuesHolder
import android.view.*

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
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
      PixelFormat.TRANSLUCENT
    )
  }
}

fun setOnTouch(
  menu: Menu,
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
      if (singleTabConfirm.onTouchEvent(event)) {
        Log.d("floatingView", "widget clicked")

        if (handleClickSingle != null) {
          handleClickSingle(view)

          if(menu.moveClean!!.visibility==View.VISIBLE) {
            menu.moveClean!!.visibility=View.GONE
            menu.moveHealthy!!.visibility=View.GONE
            menu.moveLove!!.visibility=View.GONE
            menu.moveMeal!!.visibility=View.GONE
          }
          else{
            menu.moveClean!!.visibility=View.VISIBLE
            animate(menu.moveClean!!, layoutParams.x, layoutParams.x-100, layoutParams.y, layoutParams.y-200, windowManager)
            windowManager.updateViewLayout(menu.moveClean, createLayoutParams(layoutParams.x, layoutParams.y))

            menu.moveHealthy!!.visibility=View.VISIBLE
            animate(menu.moveHealthy!!, layoutParams.x, layoutParams.x-200, layoutParams.y, layoutParams.y-80, windowManager)
            windowManager.updateViewLayout(menu.moveHealthy, createLayoutParams(layoutParams.x, layoutParams.y))

            menu.moveLove!!.visibility=View.VISIBLE
            animate(menu.moveLove!!, layoutParams.x, layoutParams.x-200, layoutParams.y, layoutParams.y+80, windowManager)
            windowManager.updateViewLayout(menu.moveLove, createLayoutParams(layoutParams.x, layoutParams.y))

            menu.moveMeal!!.visibility=View.VISIBLE
            animate(menu.moveMeal!!, layoutParams.x, layoutParams.x-100, layoutParams.y, layoutParams.y+200, windowManager)
            windowManager.updateViewLayout(menu.moveMeal, createLayoutParams(layoutParams.x, layoutParams.y))

          }
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
            windowManager.updateViewLayout(menu.moveClean, createLayoutParams(layoutParams.x-100, layoutParams.y-200))
            windowManager.updateViewLayout(menu.moveHealthy, createLayoutParams(layoutParams.x-200, layoutParams.y-80))
            windowManager.updateViewLayout(menu.moveLove, createLayoutParams(layoutParams.x-200, layoutParams.y+80))
            windowManager.updateViewLayout(menu.moveMeal, createLayoutParams(layoutParams.x-100, layoutParams.y+200))
            return true
          }
        }
      }
      return false
    }
  })
}

fun animate(v: View, startX: Int, endX: Int, startY: Int, endY: Int, windowManager: WindowManager) {

  val pvhX = PropertyValuesHolder.ofInt("x", startX, endX)
  val pvhY = PropertyValuesHolder.ofInt("y", startY, endY)

  val translator = ValueAnimator.ofPropertyValuesHolder(pvhX, pvhY)

  translator.addUpdateListener { valueAnimator ->
    val layoutParams = v.layoutParams as WindowManager.LayoutParams
    layoutParams.x = valueAnimator.getAnimatedValue("x") as Int
    layoutParams.y = valueAnimator.getAnimatedValue("y") as Int
    windowManager.updateViewLayout(v, layoutParams)
  }

  translator.duration = 500
  translator.start()
}


class SingleTapConfirm : SimpleOnGestureListener() {
  override fun onSingleTapUp(event: MotionEvent): Boolean {
    return true
  }
}
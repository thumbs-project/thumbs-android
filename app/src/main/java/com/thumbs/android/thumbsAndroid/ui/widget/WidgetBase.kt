package com.thumbs.android.thumbsAndroid.ui.widget

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.thumbs.android.thumbsAndroid.ui.menu.MenuView

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
    menuView: MenuView,
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

          if(menuView.moveClean!!.visibility==View.VISIBLE) {
            menuView.moveClean!!.visibility=View.GONE
            menuView.moveHealthy!!.visibility=View.GONE
            menuView.moveLove!!.visibility=View.GONE
            menuView.moveMeal!!.visibility=View.GONE
          }
          else{
            menuAnimate(menuView.moveClean!!, Point(layoutParams.x, layoutParams.y), Point(layoutParams.x-100,layoutParams.y-200), windowManager)
            menuAnimate(menuView.moveHealthy!!, Point(layoutParams.x, layoutParams.y), Point(layoutParams.x-200,layoutParams.y-80), windowManager)
            menuAnimate(menuView.moveLove!!, Point(layoutParams.x, layoutParams.y), Point(layoutParams.x-200,layoutParams.y+80), windowManager)
            menuAnimate(menuView.moveMeal!!, Point(layoutParams.x, layoutParams.y), Point(layoutParams.x-100,layoutParams.y+200), windowManager)
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
            windowManager.updateViewLayout(menuView.moveClean, createLayoutParams(layoutParams.x-100, layoutParams.y-200))
            windowManager.updateViewLayout(menuView.moveHealthy, createLayoutParams(layoutParams.x-200, layoutParams.y-80))
            windowManager.updateViewLayout(menuView.moveLove, createLayoutParams(layoutParams.x-200, layoutParams.y+80))
            windowManager.updateViewLayout(menuView.moveMeal, createLayoutParams(layoutParams.x-100, layoutParams.y+200))
            return true
          }
        }
      }
      return false
    }
  })
}


fun menuAnimate(view: View, startPoint: Point, endPoint: Point, windowManager: WindowManager){
  view.visibility=View.VISIBLE
  animate(view, startPoint, endPoint, windowManager)
  windowManager.updateViewLayout(view, createLayoutParams(startPoint.x, startPoint.y))
}

fun animate(v: View, startPoint: Point, endPoint: Point, windowManager: WindowManager) {
  val pvhX = PropertyValuesHolder.ofInt("x", startPoint.x, endPoint.x)
  val pvhY = PropertyValuesHolder.ofInt("y", startPoint.y, endPoint.y)

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

class Point{
  var x = 0
  var y = 0

  constructor(X: Int, Y: Int) {
    x = X
    y = Y
  }
}

class SingleTapConfirm : SimpleOnGestureListener() {
  override fun onSingleTapUp(event: MotionEvent): Boolean {
    return true
  }
}
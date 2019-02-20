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
            menu.moveQuit!!.visibility=View.GONE 
          }
          else{
            menuAnimate(menu.moveClean!!, Point(layoutParams.x, layoutParams.y), Point(layoutParams.x-30,layoutParams.y-200), windowManager)
            menuAnimate(menu.moveHealthy!!, Point(layoutParams.x, layoutParams.y), Point(layoutParams.x-150,layoutParams.y-140), windowManager)
            menuAnimate(menu.moveLove!!, Point(layoutParams.x, layoutParams.y), Point(layoutParams.x-200,layoutParams.y), windowManager)
            menuAnimate(menu.moveMeal!!, Point(layoutParams.x, layoutParams.y), Point(layoutParams.x-150,layoutParams.y+140), windowManager)
            menuAnimate(menu.moveQuit!!, Point(layoutParams.x, layoutParams.y), Point(layoutParams.x-30,layoutParams.y+200), windowManager)
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
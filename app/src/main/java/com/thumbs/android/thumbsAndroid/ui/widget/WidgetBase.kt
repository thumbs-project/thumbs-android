package com.thumbs.android.thumbsAndroid.ui.widget

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.*
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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
    menu: MenuView,
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

      var state = 0

    override fun onTouch(v: View, event: MotionEvent): Boolean {
      if (singleTabConfirm.onTouchEvent(event)) {
        Log.d("floatingView", "widget clicked")

        if (handleClickSingle != null) {
          handleClickSingle(view)

          if(state==1) {
            showingMenuIn()
            state=0
          }
          else if(state==0){
            showingMenuOut()
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
            updateView()
            return true
          }
        }
      }
      return false
    }

    private fun showingMenuIn() {
      menuAnimate(
        menu.moveClean!!,
        Point(layoutParams.x - 30, layoutParams.y - 200),
        Point(layoutParams.x, layoutParams.y),
        windowManager,
        state
      )
      menuAnimate(
        menu.moveHealthy!!,
        Point(layoutParams.x - 150, layoutParams.y - 140),
        Point(layoutParams.x, layoutParams.y),
        windowManager,
        state
      )
      menuAnimate(
        menu.moveLove!!,
        Point(layoutParams.x - 200, layoutParams.y),
        Point(layoutParams.x, layoutParams.y),
        windowManager,
        state
      )
      menuAnimate(
        menu.moveMeal!!,
        Point(layoutParams.x - 150, layoutParams.y + 140),
        Point(layoutParams.x, layoutParams.y),
        windowManager,
        state
      )
      menuAnimate(
        menu.moveQuit!!,
        Point(layoutParams.x - 30, layoutParams.y + 200),
        Point(layoutParams.x, layoutParams.y),
        windowManager,
        state
      )
    }
    private fun showingMenuOut() {
      menuAnimate(
        menu.moveClean!!,
        Point(layoutParams.x, layoutParams.y),
        Point(layoutParams.x - 30, layoutParams.y - 200),
        windowManager,
        state
      )
      menuAnimate(
        menu.moveHealthy!!,
        Point(layoutParams.x, layoutParams.y),
        Point(layoutParams.x - 150, layoutParams.y - 140),
        windowManager,
        state
      )
      menuAnimate(
        menu.moveLove!!,
        Point(layoutParams.x, layoutParams.y),
        Point(layoutParams.x - 200, layoutParams.y),
        windowManager,
        state
      )
      menuAnimate(
        menu.moveMeal!!,
        Point(layoutParams.x, layoutParams.y),
        Point(layoutParams.x - 150, layoutParams.y + 140),
        windowManager,
        state
      )
      menuAnimate(
        menu.moveQuit!!,
        Point(layoutParams.x, layoutParams.y),
        Point(layoutParams.x - 30, layoutParams.y + 200),
        windowManager,
        state
      )
      state = 1
    }

    private fun updateView() {
      windowManager.updateViewLayout(view, layoutParams)
      windowManager.updateViewLayout(menu.moveClean, createLayoutParams(layoutParams.x - 30, layoutParams.y - 200))
      windowManager.updateViewLayout(menu.moveHealthy, createLayoutParams(layoutParams.x - 150, layoutParams.y - 140))
      windowManager.updateViewLayout(menu.moveLove, createLayoutParams(layoutParams.x - 200, layoutParams.y))
      windowManager.updateViewLayout(menu.moveMeal, createLayoutParams(layoutParams.x - 150, layoutParams.y + 140))
      windowManager.updateViewLayout(menu.moveQuit, createLayoutParams(layoutParams.x - 30, layoutParams.y + 200))
    }

  })
}


fun menuAnimate(view: View, startPoint: Point, endPoint: Point, windowManager: WindowManager, state: Int){
  view.visibility=View.VISIBLE
  animate(view, startPoint, endPoint, windowManager, state)
  windowManager.updateViewLayout(view, createLayoutParams(startPoint.x, startPoint.y))
}

fun animate(v: View, startPoint: Point, endPoint: Point, windowManager: WindowManager, state: Int) {
  val pvhX = PropertyValuesHolder.ofInt("x", startPoint.x, endPoint.x)
  val pvhY = PropertyValuesHolder.ofInt("y", startPoint.y, endPoint.y)

  val translator = ValueAnimator.ofPropertyValuesHolder(pvhX, pvhY)

  translator.addUpdateListener { valueAnimator ->
    val layoutParams = v.layoutParams as WindowManager.LayoutParams
    layoutParams.x = valueAnimator.getAnimatedValue("x") as Int
    layoutParams.y = valueAnimator.getAnimatedValue("y") as Int
    windowManager.updateViewLayout(v, layoutParams)
  }
    if(state==1){
        translator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                v.visibility=View.GONE
            }
        })
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
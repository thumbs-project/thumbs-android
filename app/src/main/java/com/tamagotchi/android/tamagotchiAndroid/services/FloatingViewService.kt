package com.tamagotchi.android.tamagotchiAndroid.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.*
import android.view.animation.Animation
import com.tamagotchi.android.tamagotchiAndroid.R
import android.view.GestureDetector.SimpleOnGestureListener




class FloatingViewService : Service() {

  val windowManager by lazy {
    getSystemService(WINDOW_SERVICE) as WindowManager
  }

  val singleTabConfirm by lazy {
    GestureDetector(this,SingleTapConfirm())
  }
  var collapsedView: View? = null
  var expandedView: View? = null
  var movingAnim: Animation? = null
  var floatingView: View? = null
  var buttonClose: View? = null


  override fun onBind(intent: Intent?): IBinder? = null

  override fun onCreate() {
    super.onCreate()
    init()
  }

  fun init() {
    floatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null)

    var mParams = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
        PixelFormat.TRANSLUCENT
      )
    } else {
      WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
        PixelFormat.TRANSLUCENT
      )
    }

    windowManager.addView(floatingView, mParams)

    collapsedView = floatingView?.findViewById(R.id.layoutCollapsed)
    expandedView = floatingView?.findViewById(R.id.layoutExpanded)
    buttonClose = floatingView?.findViewById(R.id.buttonClose)

    expandedView?.setOnClickListener {
      //showLayout()
    }

    buttonClose?.setOnClickListener {
      stopSelf()
    }


    floatingView?.setOnTouchListener(object : View.OnTouchListener {
      private var initialX: Int = 0
      private var initialY: Int = 0
      private var initialTouchX: Float = 0.toFloat()
      private var initialTouchY: Float = 0.toFloat()

      override fun onTouch(v: View, event: MotionEvent): Boolean {


        if(singleTabConfirm.onTouchEvent(event)){
          // single tab
          Log.d("floatingView","widget clicked")

          return true
        }else{
          when (event.action) {
            MotionEvent.ACTION_DOWN -> {
              initialX = mParams.x
              initialY = mParams.y
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
              mParams.x = initialX + (event.rawX - initialTouchX).toInt()
              mParams.y = initialY + (event.rawY - initialTouchY).toInt()
              windowManager.updateViewLayout(floatingView, mParams)
              return true
            }
          }
        }
        return false
      }
    })
  }

  fun showLayout(){
    collapsedView?.visibility = (View.VISIBLE)
    expandedView?.visibility = (View.GONE)
  }
  fun hideLayout(){
    collapsedView?.visibility = (View.GONE)
    expandedView?.visibility = (View.VISIBLE)
  }


  override fun onDestroy() {
    super.onDestroy()
    floatingView?.let {
      windowManager?.removeView(it)
    }
  }
}

//setAnimationListener 코드
/*
    movingAnim = AnimationUtils.loadAnimation(this, R.anim.animation)
    movingAnim?.setAnimationListener(object : Animation.AnimationListener {
      override fun onAnimationStart(animation: Animation) {

      }

      override fun onAnimationEnd(animation: Animation) {
        Toast.makeText(applicationContext, "애니메이션 종료됨.", Toast.LENGTH_LONG).show()
      }

      override fun onAnimationRepeat(animation: Animation) {

      }
    })*/

class SingleTapConfirm : SimpleOnGestureListener() {

  override fun onSingleTapUp(event: MotionEvent): Boolean {
    return true
  }
}

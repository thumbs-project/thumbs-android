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
import com.tamagotchi.android.tamagotchiAndroid.views.ItemWidget
import com.tamagotchi.android.tamagotchiAndroid.views.MainWidget
import com.tamagotchi.android.tamagotchiAndroid.views.UpdownWidget

class FloatingViewService : Service() {
  val windowManager by lazy {
    getSystemService(WINDOW_SERVICE) as WindowManager
  }

  var floatingView: View? = null
  var buttonClose: View? = null

  override fun onBind(intent: Intent?): IBinder? = null

  override fun onCreate() {
    super.onCreate()
    init()
  }

  fun init() {
    MainWidget(this, windowManager)
    ItemWidget(this, windowManager)
    UpdownWidget(this, windowManager)
  }

  override fun onDestroy() {
    super.onDestroy()
    floatingView?.let {
      windowManager.removeView(it)
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
  })
*/

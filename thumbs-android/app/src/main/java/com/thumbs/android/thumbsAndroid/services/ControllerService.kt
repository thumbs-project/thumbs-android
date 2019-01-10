package com.thumbs.android.thumbsAndroid.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.view.View
import android.view.WindowManager
import com.thumbs.android.thumbsAndroid.ui.MainWidget

class ControllerService : Service() {
  var floatingView: View? = null
  val windowManager by lazy {
    getSystemService(Context.WINDOW_SERVICE) as WindowManager
  }

  private val myBinder = MyLocalBinder()

  override fun onCreate() {
    super.onCreate()
    init()
  }

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  /*
  fun getSize(main: MainWidget): RelativeLayout.LayoutParams {
    var param = RelativeLayout.LayoutParams(main.)
  } */


  inner class MyLocalBinder : Binder() {
    fun getService() : ControllerService {
      return this@ControllerService
    }

  }

  fun init() {
    val main = MainWidget(this, windowManager)
   // ItemWidget(this, windowManager)
   // UpdownWidget(this, windowManager)

  }

  override fun onDestroy() {
    super.onDestroy()
    floatingView?.let {
      windowManager.removeView(it)
    }
  }
}

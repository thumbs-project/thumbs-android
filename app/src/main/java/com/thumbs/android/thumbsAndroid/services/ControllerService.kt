package com.thumbs.android.thumbsAndroid.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.*
import com.thumbs.android.thumbsAndroid.ui.widget.ItemWidget
import com.thumbs.android.thumbsAndroid.ui.widget.MainWidget
import com.thumbs.android.thumbsAndroid.ui.widget.UpdownWidget

class ControllerService : Service() {
  var floatingView: View? = null
  val windowManager by lazy {
    getSystemService(Context.WINDOW_SERVICE) as WindowManager
  }

  override fun onCreate() {
    super.onCreate()
    init()
  }

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  fun init() {
      MainWidget(this, windowManager)
      //ItemWidget(this, windowManager)
      //UpdownWidget(this, windowManager)
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d("삭제 ","삭제")
    floatingView?.let {
      windowManager.removeView(it)
    }
  }
}

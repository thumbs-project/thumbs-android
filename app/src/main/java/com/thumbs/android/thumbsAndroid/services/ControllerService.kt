package com.thumbs.android.thumbsAndroid.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.thumbs.android.thumbsAndroid.ui.menu.MenuContract
import com.thumbs.android.thumbsAndroid.ui.widget.MainWidget
import org.koin.android.ext.android.inject

class ControllerService : Service() {
    var floatingView: View? = null

    val windowManager by lazy {
        getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }
    //  / Lazy injected Presenter instance
    val presenter: MenuContract.UserActionListerner by inject<MenuContract.UserActionListerner>()

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "ControllerService called")
        init()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun init() {
        MainWidget(this, windowManager, presenter)
        //ItemWidget(this, windowManager)
        //UpdownWidget(this, windowManager)
    }

    override fun onDestroy() {
        super.onDestroy()
        floatingView?.let {
            windowManager.removeView(it)
        }
    }
}

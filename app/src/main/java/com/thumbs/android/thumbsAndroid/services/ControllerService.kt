package com.thumbs.android.thumbsAndroid.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.ui.menu.MenuContract
import com.thumbs.android.thumbsAndroid.ui.widget.MainWidget
import com.thumbs.android.thumbsAndroid.ui.widget.createLayoutParams
import org.koin.android.ext.android.inject

class ControllerService : Service() {

    val windowManager by lazy {
        getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    private val menuPresenter: MenuContract.UserActionListerner by inject()
    var thumbsView: View? =null
    var menuView: View? =null
    override fun onCreate() {
        super.onCreate()
        thumbsView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null)
//        menuView = LayoutInflater.from(this).inflate(R.layout.activity_clean, null)
//        val thumbsView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null)

        MainWidget(this, windowManager, menuPresenter, thumbsView!!)
//        val layoutParams = createLayoutParams(0, -310)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.d("TAG", "onDestroy1 called")

        super.onDestroy()


        (applicationContext.getSystemService(Service.WINDOW_SERVICE) as WindowManager).run{
            removeView(thumbsView)
            removeView(menuView)
        }
//        (applicationContext.getSystemService(Service.WINDOW_SERVICE) as WindowManager).removeView(thumbsView)
//            (WindowManager) getApplicationContext().getSystemService(Service.WINDOW_SERVICE).removeView()
//            thumbsView = null

//        }

        Log.d("TAG", "onDestroy2 called")
    }
}

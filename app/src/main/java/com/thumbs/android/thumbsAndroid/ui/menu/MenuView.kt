package com.thumbs.android.thumbsAndroid.ui.menu

import android.app.Service
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.thumbs.android.thumbsAndroid.ui.shake.ShakeTest
import com.thumbs.android.thumbsAndroid.ui.widget.WidgetListener


class MenuView(
    service: Service,
    windowManager: WindowManager,
    layoutParams: WindowManager.LayoutParams,
    presenter: MenuContract.UserActionListerner,
    val imageListener: WidgetListener
) : MenuContract.MenuView {

    var moveClean: View? = null
    var moveHealthy: View? = null
    var moveLove: View? = null
    var moveMeal: View? = null
    var moveQuit: View? = null

    val CLEANACTION: Int = 1
    val HEALTYACTION: Int = 2
    val LOVEACTION: Int = 3
    val MEALACTION: Int = 4

    var thumbsService: Service? = null
    var thumbsWindowManager: WindowManager? = null

    val presenter = presenter.apply { attachView(this@MenuView) }

    override fun setView(imageUrl: String) {
        imageListener.setImage(imageUrl)
        if (imageUrl.substringAfterLast("/") == "dead.png") {
        val dialogIntent = Intent(thumbsService, ShakeTest::class.java)
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        thumbsService?.startActivity(dialogIntent)
//        thumbsService?.stopService(Intent(thumbsService, ControllerService::class.java))
        }
    }

    init {
        thumbsService = service
        thumbsWindowManager = windowManager
        val clean = LayoutInflater.from(service)
            .inflate(com.thumbs.android.thumbsAndroid.R.layout.activity_clean, null)
        val healty = LayoutInflater.from(service)
            .inflate(com.thumbs.android.thumbsAndroid.R.layout.activity_healthy, null)
        val love = LayoutInflater.from(service)
            .inflate(com.thumbs.android.thumbsAndroid.R.layout.activity_love, null)
        val meal = LayoutInflater.from(service)
            .inflate(com.thumbs.android.thumbsAndroid.R.layout.activity_meal, null)
        val quit = LayoutInflater.from(service)
            .inflate(com.thumbs.android.thumbsAndroid.R.layout.activity_quit, null)

        clean!!.visibility = View.GONE
        moveClean = clean
        moveClean!!.setOnClickListener { actionListener(CLEANACTION) }

        healty!!.visibility = View.GONE
        moveHealthy = healty
        moveHealthy!!.setOnClickListener { actionListener(HEALTYACTION) }

        love!!.visibility = View.GONE
        moveLove = love
        moveLove!!.setOnClickListener { actionListener(LOVEACTION) }

        meal!!.visibility = View.GONE
        moveMeal = meal
        moveMeal!!.setOnClickListener { actionListener(MEALACTION) }

        quit!!.visibility = View.GONE
        moveQuit = quit
        moveQuit!!.setOnClickListener {
            // TODO need to app exit
            service.stopSelf()
        }

        windowManager.addView(moveClean, layoutParams)
        windowManager.addView(moveHealthy, layoutParams)
        windowManager.addView(moveLove, layoutParams)
        windowManager.addView(moveMeal, layoutParams)
        windowManager.addView(moveQuit, layoutParams)
    }


    override fun actionListener(action: Int) {
        presenter.postUserEvent(action)
    }
}
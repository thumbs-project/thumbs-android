package com.thumbs.android.thumbsAndroid.ui.menu

import android.app.Service
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.R.layout
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
//        if (imageUrl.substringAfterLast("/") == "dead.png") {
//        val dialogIntent = Intent(thumbsService, ShakeTest::class.java)
//        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        thumbsService?.startActivity(dialogIntent)
//        thumbsService?.stopService(Intent(thumbsService, ControllerService::class.java))
//        }
    }

    init {
        thumbsService = service
        thumbsWindowManager = windowManager

//        val clean = LayoutInflater.from(service)
//            .inflate(layout.activity_clean, null)
//        val healty = LayoutInflater.from(service)
//            .inflate(layout.activity_healthy, null)
//        val love = LayoutInflater.from(service)
//            .inflate(layout.activity_love, null)
//        val meal = LayoutInflater.from(service)
//            .inflate(R.layout.activity_meal, null)
//        val quit = LayoutInflater.from(service)
//            .inflate(R.layout.activity_quit, null)
        initView(service)
        setClickListener(service)
        addView(windowManager, layoutParams)
    }

    private fun initView(service: Service) {
        moveClean = LayoutInflater.from(service)
            .inflate(layout.activity_clean, null)
        moveClean!!.visibility = View.GONE

        moveHealthy = LayoutInflater.from(service)
            .inflate(layout.activity_healthy, null)
        moveHealthy!!.visibility = View.GONE

        moveLove = LayoutInflater.from(service)
            .inflate(layout.activity_love, null)
        moveLove!!.visibility = View.GONE

        moveMeal = LayoutInflater.from(service)
            .inflate(layout.activity_meal, null)
        moveMeal!!.visibility = View.GONE

        moveQuit = LayoutInflater.from(service)
            .inflate(layout.activity_quit, null)
        moveQuit!!.visibility = View.GONE
    }

    private fun setClickListener(service: Service) {
        moveClean!!.setOnClickListener { actionListener(CLEANACTION) }
        moveHealthy!!.setOnClickListener { actionListener(HEALTYACTION) }
        moveLove!!.setOnClickListener { actionListener(LOVEACTION) }
        moveMeal!!.setOnClickListener { actionListener(MEALACTION) }
        moveQuit!!.setOnClickListener {
            // TODO need to app exit
            service.stopSelf()
        }
    }

    private fun addView(windowManager: WindowManager, layoutParams: WindowManager.LayoutParams) {
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
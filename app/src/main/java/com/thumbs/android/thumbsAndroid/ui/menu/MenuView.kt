package com.thumbs.android.thumbsAndroid.ui.menu

import android.app.Service
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.ui.widget.WidgetListener

class MenuView(
    service: Service,
    windowManager: WindowManager,
    layoutParams: WindowManager.LayoutParams,
    presenter: MenuContract.UserActionListerner,
    val imageListener : WidgetListener
) : MenuContract.MenuView {
    override fun setView(imageUrl: String) {
        imageListener.setImage(imageUrl)
        if( imageUrl.substringAfterLast("/") == "dead.png") {

            //TODO SHAKE

        }

    }

    var moveClean: View? = null
    var moveHealthy: View? = null
    var moveLove: View? = null
    var moveMeal: View? = null
    var moveQuit: View? = null

    val CLEANACTION: Int = 1
    val HEALTYACTION: Int = 2
    val LOVEACTION: Int = 3
    val MEALACTION: Int = 4

    val presenter = presenter.apply { attachView(this@MenuView) }

    init {
        val clean = LayoutInflater.from(service)
            .inflate(R.layout.activity_clean, null)
        val healty = LayoutInflater.from(service)
            .inflate(R.layout.activity_healthy, null)
        val love = LayoutInflater.from(service)
            .inflate(R.layout.activity_love, null)
        val meal = LayoutInflater.from(service)
            .inflate(R.layout.activity_meal, null)
        val quit = LayoutInflater.from(service)
            .inflate(R.layout.activity_quit, null)

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

        quit!!.visibility=View.GONE
        moveQuit = quit
        moveQuit!!.setOnClickListener { actionListener(MEALACTION) }

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
package com.thumbs.android.thumbsAndroid.ui.menu

import android.app.Service
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.thumbs.android.thumbsAndroid.R

class Menu{

  var moveClean: View? = null
  var moveHealthy: View? = null
  var moveLove: View? = null
  var moveMeal: View? = null
  var moveQuit: View? = null


  constructor(service: Service, windowManager: WindowManager, layoutParams: WindowManager.LayoutParams) {

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

    clean!!.visibility=View.GONE
    moveClean = clean

    healty!!.visibility=View.GONE
    moveHealthy = healty

    love!!.visibility=View.GONE
    moveLove = love

    meal!!.visibility=View.GONE
    moveMeal = meal

    quit!!.visibility=View.GONE
    moveQuit = quit


    windowManager.addView(moveClean, layoutParams)
    windowManager.addView(moveHealthy, layoutParams)
    windowManager.addView(moveLove, layoutParams)
    windowManager.addView(moveMeal, layoutParams)
    windowManager.addView(moveQuit, layoutParams)
  }
}
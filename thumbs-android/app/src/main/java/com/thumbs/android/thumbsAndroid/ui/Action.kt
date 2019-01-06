package com.thumbs.android.thumbsAndroid.ui

import android.app.Service
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.LinearLayout
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.ui.MainWidget


class Action{

  var moveview: View? = null

  constructor(service: Service, windowManager: WindowManager, layoutParams: WindowManager.LayoutParams) {

    val view = LayoutInflater.from(service)
      .inflate(R.layout.activity_action, null)


    moveview = view

    layoutParams.x = layoutParams.x-200

    windowManager.addView(view, layoutParams)
  }

}
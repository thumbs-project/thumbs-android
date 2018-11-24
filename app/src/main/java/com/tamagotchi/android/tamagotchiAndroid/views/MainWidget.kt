package com.tamagotchi.android.tamagotchiAndroid.views

import android.app.Service
import android.view.*
import com.tamagotchi.android.tamagotchiAndroid.R

class MainWidget {
  var singleTabConfirm: GestureDetector? = null;

  constructor(service: Service, windowManager: WindowManager) {
    singleTabConfirm = GestureDetector(service, SingleTapConfirm());

    var view = LayoutInflater.from(service)
      .inflate(R.layout.layout_floating_widget, null);

    var layoutParams = createLayoutParams(-100, -100);

    windowManager.addView(view, layoutParams);

    setOnTouch(view, layoutParams, singleTabConfirm!!, windowManager)
  }
}

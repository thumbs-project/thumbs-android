package com.thumbs.android.thumbsAndroid.ui.setting

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.services.ControllerService
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity2 : AppCompatActivity() {

 // lateinit var receiver: BroadcastReceiver

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_setting)

    val myToolbar = my_toolbar
    setSupportActionBar(myToolbar)
    supportActionBar!!.setTitle("환경설정")
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)


    val editBtn = editBtn
    val name = name
    editBtn.setOnClickListener {
      name.setText(edit_name.text)
    }

    val thumbs = thumbs
    val sizeBar = sizeBar

    fun testCallback(callback: ((String)->Unit)) {
      callback.invoke("hello callback")
    }

    val intent = Intent(applicationContext, ControllerService::class.java)

    sizeBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

      override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
        scaleImage(thumbs, progress, intent)
        Log.d("progress:", progress.toString())
      }

      override fun onStartTrackingTouch(seekBar: SeekBar) {

      }

      override fun onStopTrackingTouch(seekBar: SeekBar) {

      }
    })


    val switchBtn = switchBtn

    val service = ControllerService::class.java
    if(isServiceRunning(service)){
      switchBtn
    }


    switchBtn.setOnClickListener {
      /*
        if(switchBtn.isChecked){
          startService(Intent(this, ControllerService::class.java))
        } else {
          stopService(Intent(this, ControllerService::class.java))
        } */
    }

  }

  fun scaleImage(img: ImageView, progress: Int, intent: Intent) {

    /*
    var width = getResources().getDimension(img.width).toDouble()
    var height = getResources().getDimension(img.height).toDouble() */

    var width = progress*0.02.toInt()
    var height = progress*0.02.toInt()


    intent.putExtra("width", width)
    intent.putExtra("height", height)

    //startService(intent)

/*
    val layoutParams: Constraints.LayoutParams(0,0)
    layoutParams.width = width.toInt()
    layoutParams.height = height.toInt()
    view.layoutParams = layoutParams */
  }

  private fun isServiceRunning(serviceClass: Class<*>): Boolean {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
      if (serviceClass.name == service.service.className) {
        Log.d("TAG", "serviceOn")
        return true
      }
    }
    return false
  }

}



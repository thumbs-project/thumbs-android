package com.thumbs.android.thumbsAndroid.ui.setting

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.services.ControllerService
import com.thumbs.android.thumbsAndroid.showToastMessageString
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity2 : AppCompatActivity() {

 // lateinit var receiver: BroadcastReceiver

  var thumbWidth = 0
  var thumbHegiht = 0
  var publishSubject = PublishSubject.create<Pair<Int, Boolean>>()
  val PERMISSION_CODE = 2002

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_setting)

    val myToolbar = my_toolbar
    setSupportActionBar(myToolbar)
    supportActionBar?.title = ("환경설정")
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    //val intent = Intent(applicationContext, ControllerService::class.java)
    init()
  }

  fun init(){
    seekBar.progress = 50

    thumbHegiht = (thumbs.layoutParams as ViewGroup.LayoutParams).height
    thumbWidth = (thumbs.layoutParams as ViewGroup.LayoutParams).width


    publishSubject.subscribe {
      scaleImage(it.first, it.second)
    }

    seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

      override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
        publishSubject.onNext(progress to b)
        Log.d("progress:", progress.toString())
      }

      override fun onStartTrackingTouch(seekBar: SeekBar) {

      }

      override fun onStopTrackingTouch(seekBar: SeekBar) {

      }
    })

    switch_widget.setOnCheckedChangeListener { buttonView, isChecked ->
      checkPermission()
      if(isChecked){
        startService(Intent(this@SettingActivity2, ControllerService::class.java))
      } else {
        stopService(Intent(this@SettingActivity2, ControllerService::class.java))
      }
    }

  }

  fun status() {

    /* subject.subscribe {
       Log.d("subject_log", it.second)
     }*/

  }

  fun scaleImage(progress: Int, b: Boolean) {
    val params = thumbs.layoutParams as ViewGroup.LayoutParams
    Log.d("size", thumbHegiht.toString() + ":" + thumbWidth)
    params.height = thumbHegiht + 1
    params.width = thumbWidth + 1
    thumbs.layoutParams = params
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

  private fun checkPermission() {
    Intent(
      Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
      Uri.parse("package:$packageName")
    ).let {
      startActivityForResult(it, PERMISSION_CODE)
    }
  }
}



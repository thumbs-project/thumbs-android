package com.tamagotchi.android.tamagotchiAndroid.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import android.os.Build
import com.tamagotchi.android.tamagotchiAndroid.R
import com.tamagotchi.android.tamagotchiAndroid.services.FloatingViewService
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

  val PERMISSION_CODE = 2002

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    init()
  }

  fun init(){
    buttonCreateWidget.setOnClickListener {
      when {
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> {
          startService(Intent(this@MainActivity, FloatingViewService::class.java))
          finish()
        }
        Settings.canDrawOverlays(this@MainActivity) -> {
          startService(Intent(this@MainActivity, FloatingViewService::class.java))
          finish()
        }
        else -> {
          checkPermission()
          Toast.makeText(this, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show()
        }
      }



    }

  }

  private fun checkPermission(){
    Intent(
      Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
      Uri.parse("package:$packageName")
    ).let {
      startActivityForResult(it, PERMISSION_CODE)
    }
  }
}

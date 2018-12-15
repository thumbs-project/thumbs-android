package com.thumbs.android.thumbsAndroid.views

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.constants.Label
import com.thumbs.android.thumbsAndroid.services.ControllerService
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
  val PERMISSION_CODE = 2002

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)
    init()
  }

  fun init() {

    this.CreateWidgetButton.setOnClickListener {
      when {
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> {
          startService(Intent(this@SettingsActivity, ControllerService::class.java))
          finish()
        }
        Settings.canDrawOverlays(this@SettingsActivity) -> {
          startService(Intent(this@SettingsActivity, ControllerService::class.java))
          finish()
        }
        else -> {
          checkPermission()
          Toast.makeText(this, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show()
        }
      }
    }

    this.SettingButton.text = Label.OPEN_SETTINGS
    this.SettingButton.setOnClickListener {
      startActivity(Intent(settingActivityIntent()))
    }
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

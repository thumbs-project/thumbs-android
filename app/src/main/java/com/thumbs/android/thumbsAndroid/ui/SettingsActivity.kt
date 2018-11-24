package com.thumbs.android.thumbsAndroid.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.tamagotchi.android.tamagotchiAndroid.views.settingActivityIntent
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.constants.Label
import com.thumbs.android.thumbsAndroid.presenter.setting.SettingContract
import com.thumbs.android.thumbsAndroid.services.ControllerService
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.android.ext.android.inject


class SettingsActivity : BaseActivity() {
  val PERMISSION_CODE = 2002
  val presenter  by inject<SettingContract.SettingUserActionListener>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)
    init()

    //buttonSetting.setOnClickListener { startActivity(Intent (settingActivityIntent())) }
  }

  fun init() {
    presenter.load()

    buttonSetting.setOnClickListener {
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

    buttonSetting.text = Label.OPEN_SETTINGS
    buttonSetting.setOnClickListener {
    //startActivity(Intent(settingActivityIntent()))
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

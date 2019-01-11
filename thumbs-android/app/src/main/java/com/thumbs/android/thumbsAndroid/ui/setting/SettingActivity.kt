package com.thumbs.android.thumbsAndroid.ui.setting

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.constants.Label
import com.thumbs.android.thumbsAndroid.services.ControllerService
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.android.ext.android.inject


class SettingActivity : BaseActivity() {


  val PERMISSION_CODE = 2002
  val presenter  by inject<SettingContract.SettingUserActionListener>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)
    init()

    //CreateWidgetButton.setOnClickListener { startActivity(Intent (settingActivityIntent())) }
  }

  override fun startInject() {
      //presenter.attachView(this)
  }

  fun init() {

    presenter.load()

    CreateWidgetButton.setOnClickListener {
      when {
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> {
          startService(Intent(this@SettingActivity, ControllerService::class.java))
          finish()
        }
        Settings.canDrawOverlays(this@SettingActivity) -> {
          startService(Intent(this@SettingActivity, ControllerService::class.java))
          finish()
        }
        else -> {
          checkPermission()
          Toast.makeText(this, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show()
        }
      }
    }

    buttonSetting.text = Label.OPEN_SETTINGS
    buttonSetting.setOnClickListener {
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

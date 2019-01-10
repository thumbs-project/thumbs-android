package com.thumbs.android.thumbsAndroid.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.widget.Toast
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

  var myService: ControllerService? = null
  var isBound = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)
    init()

    //CreateWidgetButton.setOnClickListener { startActivity(Intent (settingActivityIntent())) }
  }

  private val Connection = object : ServiceConnection {
    override fun onServiceConnected(className: ComponentName,
                                    service: IBinder
    ) {
      val binder = service as ControllerService.MyLocalBinder
      myService = binder.getService()
      isBound = true
    }

    override fun onServiceDisconnected(name: ComponentName) {
      isBound = false
    }
  }

  fun init() {

    presenter.load()

    CreateWidgetButton.setOnClickListener {
      when {
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> {
          //startService(Intent(this@SettingsActivity, ControllerService::class.java))
          bindService(intent, Connection, Context.BIND_AUTO_CREATE)
          finish()
        }
        Settings.canDrawOverlays(this@SettingsActivity) -> {
          startService(Intent(this@SettingsActivity, ControllerService::class.java))
        //  bindService(intent, Connection, Context.BIND_AUTO_CREATE)
          finish()
        }
        else -> {
          checkPermission()
          Toast.makeText(this, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show()
        }
      }
    }

    //val size = myService?.

    buttonSetting.text = Label.OPEN_SETTINGS
    buttonSetting.setOnClickListener {
      startActivity(Intent(this@SettingsActivity, SettingActivity::class.java))
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

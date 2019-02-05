package com.thumbs.android.thumbsAndroid.ui.intro

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity
import com.thumbs.android.thumbsAndroid.ui.register.RegisterActivity


class SplashActivity : BaseActivity() {

    private val CODE_OVERLAY_PERMISSION = 2002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivityIfPermissionPass()
    }

    private fun startActivityIfPermissionPass() {
        when {
            Settings.canDrawOverlays(this) -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
            else -> {
                checkPermission()
                Toast.makeText(this, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun checkPermission() {
        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")).let{
            startActivityForResult(it, CODE_OVERLAY_PERMISSION)
        }
    }

    override fun startInject() {

    }

}

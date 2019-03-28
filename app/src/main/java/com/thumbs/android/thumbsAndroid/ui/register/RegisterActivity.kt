package com.thumbs.android.thumbsAndroid.ui.register

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.services.ControllerService
import com.thumbs.android.thumbsAndroid.showToastMessageString
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity
import com.thumbs.android.thumbsAndroid.ui.register.RegisterActivity.Const.PERMISSION_CODE
import com.thumbs.android.thumbsAndroid.ui.status.StatusActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.inject

class RegisterActivity : BaseActivity(), RegisterContract.RegisterView {


    private val presenter by inject<RegisterContract.RegisterUserActionListener>()

    object Const {
        const val PERMISSION_CODE = 2002
    }

    override fun startInject() {
        presenter.attachView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        thumbsServiceStart()
    }

    private fun thumbsServiceStart() {
        btn_next.setOnClickListener {
            presenter.createThumb(edit_name.text.toString())
            when {
                Settings.canDrawOverlays(this) -> {
                    startService(Intent(this, ControllerService::class.java))
                    finish()
                }
                else -> {
                    checkPermission()
                    Toast.makeText(this, R.string.permission_toast, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkPermission() {
        startActivityForResult(
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")),
            Const.PERMISSION_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Const.PERMISSION_CODE && resultCode == Activity.RESULT_OK) {
            if (Settings.canDrawOverlays(this)) {
                startService(Intent(this, ControllerService::class.java))
                finish()
            }
        }
    }

    override fun nextPage() {
        val intent = Intent(this, StatusActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showToast(message: String) = this.showToastMessageString(message)
    override fun isNotEmptyName(): Boolean = edit_name.text.toString().isNotBlank()

}

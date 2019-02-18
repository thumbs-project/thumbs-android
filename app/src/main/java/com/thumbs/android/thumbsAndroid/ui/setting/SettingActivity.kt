package com.thumbs.android.thumbsAndroid.ui.setting

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.dpToPixel
import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.model.ThumbSize
import com.thumbs.android.thumbsAndroid.pixelToDp
import com.thumbs.android.thumbsAndroid.services.ControllerService
import com.thumbs.android.thumbsAndroid.showToastMessageString
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity
import com.thumbs.android.thumbsAndroid.ui.setting.SettingPresenter.Companion.STEP
import kotlinx.android.synthetic.main.activity_setting.*
import org.koin.android.ext.android.inject


class SettingActivity : BaseActivity(), SettingContract.SettingView {


    val presenter by inject<SettingContract.SettingUserActionListener>()
    val PERMISSION_CODE = 2002

    override fun startInject() {
        presenter.attachView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        init()
        presenter.load()
    }

    fun init() {
        setSupportActionBar(my_toolbar as Toolbar)
        supportActionBar?.title = "환경설정"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //publishSubject.onNext(progress to b)
                presenter.controlThumbSize(
                    ThumbSize(
                        (thumbs.layoutParams as ViewGroup.LayoutParams).height,
                        (thumbs.layoutParams as ViewGroup.LayoutParams).width
                    )
                    , progress
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        switch_widget.setOnCheckedChangeListener { buttonView, isChecked ->
            checkPermission()
            if (isChecked) {
                presenter.upsert(
                    ThumbSize(
                        (thumbs.layoutParams as ViewGroup.LayoutParams).width,
                        (thumbs.layoutParams as ViewGroup.LayoutParams).height
                    )
                )
                //startService(Intent(this@SettingActivity, ControllerService::class.java))
            } else {
                //stopService(Intent(this@SettingActivity, ControllerService::class.java))
            }
        }
    }

    override fun setUi(thumb: Thumb,size : ThumbSize) {
        name.text = thumb.name
        seekBar.progress = (size.width - SettingPresenter.MIN_WIDTH_SIZE) / STEP
        setImageSize(size)
    }

    override fun setImageSize(size : ThumbSize) {
        thumbs.layoutParams = (thumbs.layoutParams as ViewGroup.LayoutParams).apply {
            this.height = size.height
            this.width = size.width
        }
    }

    override fun showToast(message: String) {
        this.showToastMessageString(message)
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



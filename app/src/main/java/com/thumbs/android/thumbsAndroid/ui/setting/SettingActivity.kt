package com.thumbs.android.thumbsAndroid.ui.setting

import android.app.ActivityManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.SeekBar
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.showToastMessageString
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_setting.*
import org.koin.android.ext.android.inject
import java.io.InputStream
import java.net.URL


class SettingActivity : BaseActivity(), SettingContract.SettingView {

    fun loadImageFromWebOperations(url: String): Drawable? {
        try {
            val `is` = URL(url).content as InputStream
            return Drawable.createFromStream(`is`, "src name")
        } catch (e: Exception) {
            return null
        }
    }

    override fun setThumbsImage(image: String) {
//        val image_from_drawable: Drawable? = this.loadImageFromWebOperations(image)
        Log.d("TAG", "ZZZ")
        val thumbsView= LayoutInflater.from(this)
            .inflate(R.layout.layout_floating_widget, null)
        getWindowManager().removeViewImmediate(thumbsView)
    }

    val presenter by inject<SettingContract.SettingUserActionListener>()

    override fun startInject() {
        presenter.attachView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        presenter.load()
    }

    override fun setUi(thumb: Thumb) {
        val myToolbar = my_toolbar
        setSupportActionBar(myToolbar)
        supportActionBar!!.setTitle("환경설정")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        name.text = thumb.name

        editBtn.setOnClickListener {
            thumb.name = edit_name.text.toString()
            name.text = thumb.name
        }

        val thumbs = thumbs
        val sizeBar = sizeBar


        sizeBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                scaleImage(thumbs, progress)
                Log.d("progress:", progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })


        val switchBtn = switchBtn
        switchBtn.setOnClickListener {
            /*
              if(switchBtn.isChecked){
                startService(Intent(this, ControllerService::class.java))
              } else {
                stopService(Intent(this, ControllerService::class.java))
              } */
        }
    }

    override fun showToast(message: String) {
        this.showToastMessageString(message)
    }

    //mvp end.

    fun scaleImage(img: ImageView, progress: Int) {

        /*
        var width = getResources().getDimension(img.width).toDouble()
        var height = getResources().getDimension(img.height).toDouble() */

        var width = progress * 0.02.toInt()
        var height = progress * 0.02.toInt()


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



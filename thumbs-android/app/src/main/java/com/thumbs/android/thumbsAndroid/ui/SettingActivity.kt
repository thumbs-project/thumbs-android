package com.thumbs.android.thumbsAndroid.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import com.thumbs.android.thumbsAndroid.R
import kotlinx.android.synthetic.main.activity_setting.*



class SettingActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_setting)

    val myToolbar = my_toolbar
    setSupportActionBar(myToolbar)
    supportActionBar!!.setTitle("환경설정")
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)


    val editBtn = editBtn
    val name = name
    editBtn.setOnClickListener {
      name.setText(edit_name.text)
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

    }
  }

  fun scaleImage(img: ImageView, progress: Int) {

    if(progress<=40){
      sizeBar.setProgress(40)
    }

    var layoutParams = LinearLayout.LayoutParams(progress*3.5.toInt(), progress*3.5.toInt())
    img.setLayoutParams(layoutParams)
  }
}



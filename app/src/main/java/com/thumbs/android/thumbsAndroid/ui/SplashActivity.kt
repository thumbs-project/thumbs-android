package com.thumbs.android.thumbsAndroid.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.thumbs.android.thumbsAndroid.R
import android.content.Intent
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)

        finish()
    }
}

package com.thumbs.android.thumbsAndroid.ui.intro

import android.os.Bundle
import android.content.Intent
import com.thumbs.android.thumbsAndroid.ui.register.RegisterActivity
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)

        finish()
    }

    override fun startInject() {

    }

}

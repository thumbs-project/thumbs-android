package com.thumbs.android.thumbsAndroid

import android.app.Application
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.thumbs.android.thumbsAndroid.core.thumbsAppModule
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity
import org.koin.android.ext.android.startKoin

class CoreApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, thumbsAppModule)
    }
}

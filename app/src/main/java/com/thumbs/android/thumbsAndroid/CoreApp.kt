package com.thumbs.android.thumbsAndroid

import android.app.Application
import com.thumbs.android.thumbsAndroid.core.modules.thumbsAppModule
import org.koin.android.ext.android.startKoin

class CoreApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, thumbsAppModule)
    }
}

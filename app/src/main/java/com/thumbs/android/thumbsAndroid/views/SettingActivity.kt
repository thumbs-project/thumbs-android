package com.thumbs.android.thumbsAndroid.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceActivity
import com.thumbs.android.thumbsAndroid.R

fun Context.settingActivityIntent(): Intent {
  return Intent(this, SettingActivity::class.java)
}

class SettingActivity : PreferenceActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.preference)
  }
}

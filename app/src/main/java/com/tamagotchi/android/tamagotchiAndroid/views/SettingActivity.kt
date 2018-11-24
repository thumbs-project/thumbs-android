package com.tamagotchi.android.tamagotchiAndroid.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceActivity
import com.tamagotchi.android.tamagotchiAndroid.R

fun Context.settingActivityIntent(): Intent {
  return Intent(this, SettingActivity::class.java)
}

class SettingActivity : PreferenceActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.preference)
  }
}

package com.thumbs.android.thumbsAndroid.ui.status

import android.content.Intent
import android.os.Bundle
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.showToastMessageString
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity
import com.thumbs.android.thumbsAndroid.ui.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_status.*
import org.koin.android.ext.android.inject

class StatusActivity : BaseActivity(), StatusContract.StatusView {

    val presenter  by inject<StatusContract.StatusUserActionListener>()

    override fun startInject() {
        presenter.attachView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)
        presenter.loadThumb()
    }

    override fun setUi(thumb: Thumb) {
        tv_nickname.text = thumb.name
        progress_love.progress = thumb.condition.affection.value ?: 0
        progress_clean.progress = thumb.condition.hygiene?.value ?: 0
        progress_health.progress = thumb.condition.health?.value ?: 0
        progress_meal.progress = thumb.condition.satiety?.value ?: 0

        tv_clean_percent.text = (thumb.condition.hygiene?.value ?: 0).toString() + "%"
        tv_health_percent.text = (thumb.condition.health?.value ?: 0).toString() + "%"
        tv_love_percent.text = (thumb.condition.affection?.value ?: 0).toString() + "%"
        tv_meal_percent.text = (thumb.condition.satiety?.value ?: 0).toString() + "%"

        val settingBtn = settingBtn
        settingBtn.setOnClickListener() {
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }

    override fun showToast(message: String) = this.showToastMessageString(message)
}

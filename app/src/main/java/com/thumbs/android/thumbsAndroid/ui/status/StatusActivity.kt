package com.thumbs.android.thumbsAndroid.ui.status

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.showToastMessageString
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity
import com.thumbs.android.thumbsAndroid.ui.setting.SettingActivity
import com.thumbs.android.thumbsAndroid.ui.setting.SettingPresenter
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_status.*
import org.koin.android.ext.android.inject

class StatusActivity : BaseActivity(), StatusContract.StatusView {

    val presenter by inject<StatusContract.StatusUserActionListener>()

    override fun startInject() {
        presenter.attachView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        presenter.loadThumb()

        iv_setting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        icon_reload.setOnClickListener {
            presenter.loadThumb()
        }
    }

    override fun setUi(thumb: Thumb) {
        tv_nickname.text = thumb.name
        progress_love.progress = thumb.condition.affection.value ?: 0
        progress_clean.progress = thumb.condition.hygiene.value ?: 0
        progress_health.progress = thumb.condition.health.value ?: 0
        progress_meal.progress = thumb.condition.satiety.value ?: 0

        tv_clean_percent.text = (thumb.condition.hygiene?.value ?: 0).toString() + "%"
        tv_health_percent.text = (thumb.condition.health?.value ?: 0).toString() + "%"
        tv_love_percent.text = (thumb.condition.affection?.value ?: 0).toString() + "%"
        tv_meal_percent.text = (thumb.condition.satiety?.value ?: 0).toString() + "%"

        Picasso.with(this).load(thumb.image).into(iv_thumb)

        iv_thumb.layoutParams = (iv_thumb.layoutParams as ViewGroup.LayoutParams).apply {
            this.height = SettingPresenter.MIN_HEIGHT_SIZE
            this.width = SettingPresenter.MIN_HEIGHT_SIZE
        }
    }
    override fun showToast(message: String) = this.showToastMessageString(message)
    override fun loadSuccess() = showToast("Thumbs 새로 고침")
    override fun loadFail() = showToast("Thumbs 불러오기 실패")

}

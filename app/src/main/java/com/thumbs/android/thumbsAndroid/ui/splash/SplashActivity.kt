package com.thumbs.android.thumbsAndroid.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.REVERSE
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.widget.Toast
import com.daimajia.easing.Glider
import com.daimajia.easing.Skill
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.model.Thumb
import com.thumbs.android.thumbsAndroid.services.ControllerService
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity
import com.thumbs.android.thumbsAndroid.ui.register.RegisterActivity
import com.thumbs.android.thumbsAndroid.ui.status.StatusActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.ext.android.inject


class SplashActivity : BaseActivity(), SplashContract.SplashView {

    val presenter by inject<SplashContract.SplashUserActionListener>()


    private val CODE_OVERLAY_PERMISSION = 2002

    override fun startInject() {
        presenter.attachView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        nextActivityIfPermissionPass()
    }

    private fun nextActivityIfPermissionPass() {

        splashAnimationStart()

        val runnable = Runnable {
            when {
                Settings.canDrawOverlays(this) -> {
                    presenter.loadThumbsData()
                }
                else -> {
                    checkPermission()
                    Toast.makeText(this, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        Handler().apply {
            postDelayed(runnable, 5500)
        }
    }

    private fun splashAnimationStart() {
        val count = 3
        val anim = AnimatorSet()
        anim.duration = 700L

        anim.playTogether(
            upDownObjectAnim(count),
            scaleObjectAnim(count),
            cubicObjectAnim(count),
            cubicValueAnim(count)
        )
        anim.start()
    }


    private fun cubicValueAnim(count: Int): ValueAnimator? {
        return Glider.glide(Skill.CubicEaseInOut, 700f, ObjectAnimator.ofFloat(0f, -140f).apply {
            duration = 700
            addUpdateListener {
                it.repeatCount = count
                it.repeatMode = REVERSE
                splash_face.translationY = it.animatedValue as Float
            }
        })
    }

    private fun cubicObjectAnim(count: Int): ValueAnimator? {
        return Glider.glide(Skill.CubicEaseInOut, 700f,
            ObjectAnimator.ofFloat(splash_shadow, "alpha", 0.7f, 0.3f).apply {
                this.repeatCount = count
                this.repeatMode = REVERSE
            })
    }

    private fun scaleObjectAnim(count: Int): ValueAnimator? {
        return Glider.glide(Skill.CubicEaseInOut, 700f,
            ObjectAnimator.ofFloat(splash_body, "scaleY", 0.9f, 1.0f).apply {
                this.repeatCount = count
                this.repeatMode = REVERSE
            })
    }

    private fun upDownObjectAnim(count: Int): ValueAnimator? {
        return Glider.glide(Skill.CubicEaseInOut, 700f,
            ObjectAnimator.ofFloat(0f, -150f).apply {
                duration = 700
                addUpdateListener {
                    it.repeatCount = count
                    it.repeatMode = REVERSE
                    splash_body.translationY = it.animatedValue as Float
                }
            })
    }

    private fun checkPermission() {
        startActivityForResult(
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")),
            CODE_OVERLAY_PERMISSION
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_OVERLAY_PERMISSION) {
            if (Settings.canDrawOverlays(this)) {
                presenter.loadThumbsData()
            }
        }
    }

    override fun success(thumbs: Thumb) {
        startService(Intent(this, ControllerService::class.java))
        startActivity(Intent(this, StatusActivity::class.java))
        finish()
    }

    override fun fail() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

}

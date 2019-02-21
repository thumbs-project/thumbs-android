package com.thumbs.android.thumbsAndroid.ui.intro

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.REVERSE
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

    val presenter by inject<SplashContract.SplashUserActionListerner>()


    private val CODE_OVERLAY_PERMISSION = 2002

    override fun startInject() {
        presenter.attachView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivityIfPermissionPass()
    }

    private fun startActivityIfPermissionPass() {
        val count = 7
        val anim = AnimatorSet()
        anim.duration = 700L
        anim.playTogether(
            Glider.glide(Skill.CubicEaseInOut, 700f,
                ObjectAnimator.ofFloat(0f, -150f).apply {
                    duration = 700
                    addUpdateListener {
                        it.repeatCount = count
                        it.repeatMode = REVERSE
                        splash_body.translationY = it.animatedValue as Float
                    }
                }),
            Glider.glide(Skill.CubicEaseInOut, 700f,
                ObjectAnimator.ofFloat(splash_body, "scaleY", 0.9f, 1.0f).apply {
                    this.repeatCount = count
                    this.repeatMode = REVERSE
                }),
            Glider.glide(Skill.CubicEaseInOut, 700f,
                ObjectAnimator.ofFloat(splash_shadow, "alpha", 0.7f, 0.3f).apply {
                    this.repeatCount = count
                    this.repeatMode = REVERSE
                }),
            Glider.glide(Skill.CubicEaseInOut, 700f, ObjectAnimator.ofFloat(0f, -140f).apply {
                duration = 700
                addUpdateListener {
                    it.repeatCount = count
                    it.repeatMode = REVERSE
                    splash_face.translationY = it.animatedValue as Float
                }
            })
        )
        anim.start()

        val runnable = Runnable {
            when {
                Settings.canDrawOverlays(this) -> {
                    presenter.loadThumbsData()
                }
                else -> {
                    checkPermission()
                    Toast.makeText(this, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }
        }

        Handler().apply {
            postDelayed(runnable, 5500)
        }
    }

    private fun checkPermission() {
        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            .let {
                startActivityForResult(it, CODE_OVERLAY_PERMISSION)
            }
    }

    override fun success(thumbs: Thumb) {
        val intent = Intent(this, StatusActivity::class.java)
        startService(Intent(this, ControllerService::class.java))
        startActivity(intent)
        finish()
    }

    override fun fail() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}

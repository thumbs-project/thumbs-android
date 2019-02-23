package com.thumbs.android.thumbsAndroid.ui.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Service
import android.view.GestureDetector
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.ui.menu.MenuContract
import com.thumbs.android.thumbsAndroid.ui.menu.MenuView

class MainWidget {
    var singleTabConfirm: GestureDetector? = null

    constructor(service: Service, windowManager: WindowManager, presenter: MenuContract.UserActionListerner, thumbsView:View){
        singleTabConfirm = GestureDetector(service, SingleTapConfirm());

        val layoutParams = createLayoutParams(0, -310)
        val image = thumbsView.findViewById<ImageView>(R.id.icon_thu)
        windowManager.addView(thumbsView, layoutParams)

//        val default = "https://s3.ap-northeast-2.amazonaws.com/rohi-thumbs/image-xxhdpi/normal.png"
        presenter.getDefaultImageUrl(service, image)


        ObjectAnimator.ofFloat(30f, -70f).apply {
          addUpdateListener {
              it.duration=700
              it.repeatCount=ValueAnimator.INFINITE
              it.repeatMode = ValueAnimator.REVERSE
              thumbsView.translationY = it.animatedValue as Float
              windowManager.updateViewLayout(thumbsView, layoutParams)
          }
      }.start()

      ObjectAnimator.ofFloat(50f, -70f).apply {
          addUpdateListener {
              it.duration=700
              it.repeatCount=ValueAnimator.INFINITE
              it.repeatMode = ValueAnimator.REVERSE
              //  it.repeatMode=ValueAnimator.RESTART
              thumbsView.translationY = it.animatedValue as Float
              windowManager.updateViewLayout(thumbsView, layoutParams)
          }
      }.start()

        val menu = MenuView(service, windowManager, layoutParams, presenter, object : WidgetListener{
            override fun setImage(imageUrl: String) {
                Picasso.with(service)
                    .load(imageUrl)
                    .resize(200,200)
                    .into(image)
            }
        })

        setOnTouch(
            menu,
            thumbsView,
            layoutParams,
            singleTabConfirm!!,
            windowManager,
            this::handleSingleClick
        )
    }


    fun handleSingleClick(view: View) {
        /* userRepository.getStatus(StatusRequestParam(
           123412341234,
           "wash"
         )).subscribe({ result ->
             Toast.makeText(
               view.context,
               result.property + " " + result.request_id,
               Toast.LENGTH_SHORT
             )
               .show()
           }, { throwable ->
             throwable.printStackTrace()
           })*/

    }
}

interface WidgetListener{
    fun setImage(imageUrl : String)
}
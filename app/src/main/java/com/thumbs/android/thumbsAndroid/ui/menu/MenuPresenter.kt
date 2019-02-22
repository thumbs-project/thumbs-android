package com.thumbs.android.thumbsAndroid.ui.menu

import android.annotation.SuppressLint
import com.thumbs.android.thumbsAndroid.model.UserAction
import com.thumbs.android.thumbsAndroid.repositories.UserEventRepository

class MenuPresenter(val menuRepository: UserEventRepository) :
    MenuContract.UserActionListerner {

    var menuView: MenuContract.MenuView? = null
    val CLEANACTION: Int = 1
    val HEALTYACTION: Int = 2
    val LOVEACTION: Int = 3
    val MEALACTION: Int = 4

    override fun attachView(view: MenuContract.MenuView) {
        menuView = view
    }

    @SuppressLint("CheckResult")
    override fun postUserEvent(action: Int) {
        val userAction: UserAction = when (action) {
            CLEANACTION -> {
                UserAction(12345, "CLEAN", "")
            }
            HEALTYACTION -> {
                UserAction(12345, "CURE", "")
            }
            LOVEACTION -> {
                UserAction(12345, "TOUCH", "")
            }
            MEALACTION -> {
                UserAction(12345, "EAT", "")
            }
            else -> UserAction(12345, "CLEAN", "")
        }
        menuRepository.sendEvent(1, userAction)
            .subscribe({ thumb ->
                menuView?.setView(thumb.image)
            }, {
                it.printStackTrace()
            })
    }

    override fun removeView() {
        this.menuView = null
    }
}

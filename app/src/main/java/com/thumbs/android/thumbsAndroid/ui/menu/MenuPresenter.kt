package com.thumbs.android.thumbsAndroid.ui.menu

import android.util.Log
import com.thumbs.android.thumbsAndroid.model.UserAction
import com.thumbs.android.thumbsAndroid.repositories.UserEventRepository

class MenuPresenter(val menuRepository: UserEventRepository) : MenuContract.UserActionListerner {

    val CLEANACTION: Int = 1
    val HEALTYACTION: Int = 2
    val LOVEACTION: Int = 3
    val MEALACTION: Int = 4
    override fun postUserEvent(action: Int) {
        Log.d("TAG", "Zzzzzzzzzź")

        val userAction: UserAction? = when (action) {
            CLEANACTION -> {
                UserAction(1, "CLEAN", {})
            }
            HEALTYACTION -> {
                UserAction(1, "CURE", {})
            }
            LOVEACTION -> {
                UserAction(1, "TOUCH", {})
            }
            MEALACTION -> {
                UserAction(1, "EAT", {})
            }
            else -> {null}
        }
        menuRepository.sendEvent(1, userAction)
    }

//        val requestId: Long,
//        val event: String,
//        val payload: Any = Any()
//      var userAction:UserAction = UserAction(1, )
//        TOUCH | CURE | CLEAN | EAT | UNDO_TOUCH | UNDO_CURE | UNDO_CLEAN | UNDO_EAT",

}

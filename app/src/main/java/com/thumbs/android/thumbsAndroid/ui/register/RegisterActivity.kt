package com.thumbs.android.thumbsAndroid.ui.register

import android.content.Intent
import android.os.Bundle
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.services.ControllerService
import com.thumbs.android.thumbsAndroid.showToastMessageString
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity
import com.thumbs.android.thumbsAndroid.ui.status.StatusActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.inject

class RegisterActivity : BaseActivity(), RegisterContract.RegisterView {


    val presenter by inject<RegisterContract.RegisterUserActionListener>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_next.setOnClickListener {
            presenter.createThumb(edit_name.text.toString())
        }
    }

    override fun startService() {
        startService(Intent(this, ControllerService::class.java))
    }

    override fun createThumbsFail() {
        this.showToast("등록이 실패되었습니다. 서버 체크가 필요!")
    }

    override fun startInject() {
        presenter.attachView(this)
    }

    override fun nextPage() {
        val intent = Intent(this, StatusActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun isNotEmptyName(): Boolean = edit_name.text.toString().isNotBlank()

    override fun showToast(message: String) = this.showToastMessageString(message)

}

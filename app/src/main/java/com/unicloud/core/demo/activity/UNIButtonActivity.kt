package com.unicloud.core.demo.activity

import android.os.Bundle
import android.util.Log
import com.unicloud.core.demo.R
import com.unicloud.core.mvvm.BaseActivity
import com.unicloud.core.mvvm.NoViewModel
import com.unicloud.core.mvvm.event.Message
import kotlinx.android.synthetic.main.activity_uni_button.*

class UNIButtonActivity : BaseActivity<NoViewModel>() {

    override fun layoutId(): Int =
        R.layout.activity_uni_button

    override fun startObserve() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        title = "UNIButton"
    }

    override fun initData() {
        btn2.setOnClickListener {
            btn1.isEnabled = !btn1.isEnabled
            btn1.invalidate()
        }
    }

    override fun handleEvent(msg: Message) {
        super.handleEvent(msg)
        Log.d("event", msg.toString())
    }

    override fun showLoading() {
        super.showLoading()
    }

    override fun dismissLoading() {
        super.dismissLoading()
    }
}
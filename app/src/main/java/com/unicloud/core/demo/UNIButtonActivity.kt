package com.unicloud.core.demo

import android.os.Bundle
import android.util.Log
import com.unicloud.core.mvvm.BaseActivity
import com.unicloud.core.mvvm.NoViewModel
import com.unicloud.core.mvvm.event.Message

class UNIButtonActivity : BaseActivity<NoViewModel>() {

    override fun layoutId(): Int = R.layout.activity_uni_button

    override fun startObserve() {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

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
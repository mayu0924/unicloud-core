package com.unicloud.core.demo.activity

import android.os.Bundle
import android.util.Log
import com.unicloud.core.demo.R
import com.unicloud.core.mvvm.BaseActivity
import com.unicloud.core.mvvm.NoViewModel
import com.unicloud.core.mvvm.event.Message

class RWidgetActivity : BaseActivity<NoViewModel>() {

    override fun layoutId(): Int =
        R.layout.activity_widget

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
}
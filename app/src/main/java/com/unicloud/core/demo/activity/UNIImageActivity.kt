package com.unicloud.core.demo.activity

import android.os.Bundle
import android.util.Log
import com.blankj.utilcode.util.ToastUtils
import com.unicloud.core.demo.R
import com.unicloud.core.mvvm.BaseActivity
import com.unicloud.core.mvvm.NoViewModel
import com.unicloud.core.mvvm.event.Message
import kotlinx.android.synthetic.main.activity_uni_text.*

class UNIImageActivity : BaseActivity<NoViewModel>() {

    override fun layoutId(): Int =
        R.layout.activity_uni_image

    override fun startObserve() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        title = "UNIImageView"
    }

    override fun initData() {

    }

    override fun handleEvent(msg: Message) {
        super.handleEvent(msg)
        Log.d("event", msg.toString())
    }
}
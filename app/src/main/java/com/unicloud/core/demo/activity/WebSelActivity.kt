package com.unicloud.core.demo.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.unicloud.core.demo.R
import com.unicloud.core.mvvm.BaseActivity
import com.unicloud.core.mvvm.NoViewModel
import com.unicloud.core.mvvm.event.Message
import com.unicloud.core.mvvm.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_web_select.*


class WebSelActivity : BaseActivity<NoViewModel>() {

    override fun layoutId(): Int = R.layout.activity_web_select

    override fun startObserve() {

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtil.setDarkMode(this)
        webview.setOnClickListener {
            startActivity(Intent(this, WebActivity::class.java))
        }
        x5webView.setOnClickListener {
            startActivity(Intent(this, X5WebActivity::class.java))
        }
    }

    override fun initData() {
    }

    override fun handleEvent(msg: Message) {
        super.handleEvent(msg)
        Log.d("event", msg.toString())
    }
}
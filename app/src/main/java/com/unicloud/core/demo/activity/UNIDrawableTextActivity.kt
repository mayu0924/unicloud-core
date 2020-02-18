package com.unicloud.core.demo.activity

import android.os.Bundle
import android.util.Log
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.unicloud.core.demo.R
import com.unicloud.core.mvvm.BaseActivity
import com.unicloud.core.mvvm.NoViewModel
import com.unicloud.core.mvvm.event.Message
import kotlinx.android.synthetic.main.activity_uni_drawable.*
import kotlinx.android.synthetic.main.activity_uni_text.*

class UNIDrawableTextActivity : BaseActivity<NoViewModel>() {

    override fun layoutId(): Int =
        R.layout.activity_uni_drawable

    override fun startObserve() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        title = "UNIText"
    }

    override fun initData() {
        udt10.setOnClickListener {
            udt10.setTextColor(resources.getColor(R.color.colorAccent))
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
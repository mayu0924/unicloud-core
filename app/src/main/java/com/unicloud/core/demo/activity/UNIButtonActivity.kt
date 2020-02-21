package com.unicloud.core.demo.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.blankj.utilcode.util.ToastUtils
import com.unicloud.core.demo.R
import com.unicloud.core.mvvm.BaseActivity
import com.unicloud.core.mvvm.NoViewModel
import com.unicloud.core.mvvm.event.Message
import kotlinx.android.synthetic.main.activity_uni_button.*

class UNIButtonActivity : BaseActivity<NoViewModel>() {

    override fun layoutId(): Int =
        R.layout.activity_uni_button

    override fun toolbarMenuRes(): Int? = R.menu.menu_button

    override fun startObserve() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        toolBar.init(this, R.mipmap.ic_toolbar_more_white)
            .navigationClickListener { finish() }
            .menuItemClickListener {
                when (it.itemId) {
                    R.id.menu_share -> {
                        ToastUtils.showShort(it.title)
                    }
                }
            }
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
}
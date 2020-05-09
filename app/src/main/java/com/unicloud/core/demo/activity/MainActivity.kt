package com.unicloud.core.demo.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.orhanobut.logger.Logger
import com.unicloud.core.demo.R
import com.unicloud.core.demo.activity.vm.MainViewModel
import com.unicloud.core.mvvm.BaseActivity
import com.unicloud.core.mvvm.event.Message
import com.unicloud.core.mvvm.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainViewModel>() {

    override fun layoutId(): Int = R.layout.activity_main

    override fun setToolbar(): Toolbar = toolBar

    override fun toolbarMenuRes(): Int? = R.menu.menu_main

    override fun startObserve() {
        viewModel.mArticleListBean.observe(this, Observer {
//            uText.setText(it.datas[0].title)
        })

        viewModel.allUser.observe(this, Observer {
            Logger.d(it.size)
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtil.setLightMode(this)
        toolBar.init(this, R.mipmap.ic_toolbar_more_white)
            .menuItemClickListener {
                when (it.itemId) {
                    R.id.menu_share -> {
                        ToastUtils.showShort(it.title)
                    }
                    R.id.menu_camera -> {
                        ToastUtils.showShort(it.title)
                    }
                    R.id.menu_storage -> {
                        ToastUtils.showShort(it.title)
                    }
                }
            }
        toolBar.updateTitleCenter()
        btn_anim.setOnClickListener {
            startActivity(Intent(this, NavigationActivity::class.java))
        }
    }

    fun setTitleCenter(toolbar: Toolbar) {
        val title = "title"
        val originalTitle = toolbar.title
        toolbar.title = title
        for (i in 0 until toolbar.childCount) {
            val view = toolbar.getChildAt(i)
            if (view is TextView) {
                val textView = view
                if (title == textView.text) {
                    textView.gravity = Gravity.CENTER
                    val params =
                        Toolbar.LayoutParams(
                            Toolbar.LayoutParams.WRAP_CONTENT,
                            Toolbar.LayoutParams.MATCH_PARENT
                        )
                    params.gravity = Gravity.CENTER
                    textView.layoutParams = params
                    toolbar.title = originalTitle
                }
            }

        }
    }


    override fun initData() {

    }

    override fun handleEvent(msg: Message) {
        super.handleEvent(msg)
        Log.d("event", msg.toString())
    }
}
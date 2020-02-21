package com.unicloud.core.demo.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.unicloud.core.demo.R
import com.unicloud.core.demo.activity.vm.MainViewModel
import com.unicloud.core.mvvm.BaseActivity
import com.unicloud.core.mvvm.event.Message
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainViewModel>() {

    override fun layoutId(): Int = R.layout.activity_main

    override fun toolbarMenuRes(): Int? = R.menu.menu_main

    override fun startObserve() {
        viewModel.mArticleListBean.observe(this, Observer {
            //            chapterTitle.text = it.datas[0].title
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
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
    }


    override fun initData() {
//        viewModel.getHomeArticles()
//        viewModel.download()
//        chapterTitle.setOnClickListener {
//            viewModel.upload()
//        }

        uniBtn.setOnClickListener {
            startActivity(Intent(this, UNIButtonActivity::class.java))
        }
        uniText.setOnClickListener {
            startActivity(Intent(this, UNITextActivity::class.java))
        }
        uniDrawableText.setOnClickListener {
            startActivity(Intent(this, UNIDrawableTextActivity::class.java))
        }
        uniImageView.setOnClickListener {
            startActivity(Intent(this, UNIImageActivity::class.java))
        }
        rButton.setOnClickListener {
            startActivity(Intent(this, RWidgetActivity::class.java))
        }
    }

    override fun handleEvent(msg: Message) {
        super.handleEvent(msg)
        Log.d("event", msg.toString())
    }
}
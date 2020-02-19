package com.unicloud.core.demo.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.unicloud.core.demo.activity.vm.MainViewModel
import com.unicloud.core.demo.R
import com.unicloud.core.mvvm.BaseActivity
import com.unicloud.core.mvvm.event.Message
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {

    override fun layoutId(): Int = R.layout.activity_main

    override fun startObserve() {
        viewModel.mArticleListBean.observe(this, Observer {
            //            chapterTitle.text = it.datas[0].title
        })
    }

    override fun initView(savedInstanceState: Bundle?) {

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

    override fun showLoading() {
        super.showLoading()
    }

    override fun dismissLoading() {
        super.dismissLoading()
    }
}
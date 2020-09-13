package com.unicloud.core.demo.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.orhanobut.logger.Logger
import com.unicloud.core.demo.R
import com.unicloud.core.demo.activity.vm.MainViewModel
import com.unicloud.core.demo.model.bean.ArticleListBean
import com.unicloud.core.demo.model.bean.User
import com.unicloud.core.mvvm.BaseActivity
import com.unicloud.core.mvvm.data.DataStoreHelper
import com.unicloud.core.mvvm.data.SharedPreferencesHelper
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
        btn_meeting.setOnClickListener {
        }

        SharedPreferencesHelper.sp.edit().putString("name", "jack").apply()

        LogUtils.d(SharedPreferencesHelper.sp.getString("name", "no data"))

        DataStoreHelper.getValue<String>(
            scope = viewModel.viewModelScope,
            key = preferencesKey("name"),
            defaultValue = "no data",
            callback = {
                LogUtils.d(it)
            })

        DataStoreHelper.save<User>(
            scope = viewModel.viewModelScope,
            key = preferencesKey<User>("user"),
            value = User("jack", 12),
            {}
        )

        DataStoreHelper.getValue<User>(
            scope = viewModel.viewModelScope,
            key = preferencesKey("article"),
            defaultValue = User("jack123", 14),
            callback = {
                LogUtils.d(it)
            })
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